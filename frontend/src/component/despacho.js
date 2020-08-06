import React, { Component } from 'react';
import SelMaterial from './elements/selMaterial'
import NumberFormat from 'react-number-format';
import api from '../util/api';
import Msg from './elements/msg';
import _ from 'lodash';

class Despacho extends Component {

    state = {inventario: {}, touched: {}, error: {}, rows: null, 
            msg: null, status: null}

    onSubmit() {
            if(_.isEmpty(this.items)) {
                return null;
            }
            var items = Object.values(this.items)
                .map(i => { return {cantidadDespacho:i.cantidadDespacho, idmaterial: i.material.idmaterial, idpos: i.pos.idpos}})
                api().post('/od', items)
                    .then(res => {
                        this.setState({ ...this.state, msg: res.data.message, status: res.status
                        })
                    })
                    .catch(e => {
                        this.setState({ ...this.state, errorResponse: e.response})
                    });
            // window.location.href = '/despacho';
        }

    onBlurCantidad(e) {
        var idInventario = e.target.getAttribute('data-key');
        var touched = this.state.touched;
        touched[idInventario] = true;
        this.setState({touched});
        this.testErrorCantidad(e);
    }

    onChangeCantidad(e) {
        var idInventario = e.target.getAttribute('data-key');
        var item = _.mapKeys([{...this.state.rows[idInventario],
            cantidadDespacho:parseFloat(e.target.value.replace(',','.')) }],
            'idInventario');
        this.items = { ...this.items, ...item }
        console.log(this.items);
        this.testErrorCantidad(e);
    }

    testErrorCantidad(e) {
        var idInventario = e.target.getAttribute('data-key');
        var error = this.state.error;
        if(e.target.value == '')  {
            error[idInventario] = 'Se debe ingresar un valor';
            this.setState({error})
        } else if(this.items[idInventario].cantidadDespacho>this.items[idInventario].cantidad) {
            error[idInventario] = 'Sin Stock';
            this.setState({error})
        } else {
            error[idInventario] = false;
            this.setState({error})
        }
    }

    renderInput(i) {
        var classNam='form-control';
        if(this.state.touched[i.idInventario]) {
            classNam = `form-control ${this.state.error[i.idInventario] ? ' is-invalid' : ' is-valid'}`;
        }
        return (<NumberFormat data-key={i.idInventario}
                    stock={i.cantidad}
                    onBlur={(e) => this.onBlurCantidad(e)}
                    onChange={e => this.onChangeCantidad(e)}
                    className={classNam}
                    autoComplete="off"
                    thousandSeparator="."
                    decimalSeparator=","/>
                )
    }

    renderContent() {
        var rows = Object.values(this.state.rows);
        return (rows.map(  i => {
            return( <a key={i.idInventario} href="#" data-key={i.idInventario}
                        className="list-group-item list-group-item-action ">
                        <div className="d-flex w-100 justify-content-between">
                        <h5 className="mb-1">Cantidad Despacho</h5>
                        <small><strong>Stock: </strong>{i.cantidad}</small>
                        </div>
                        {this.renderInput(i)}
                        <div className="invalid-feedback">
                            {this.state.error[i.idInventario]}
                        </div>
                        <small>Ubicación: {i.pos.nombre}</small>
                    </a>
                    );
        })
    );
    }

    fetchInventario(idmaterial) {
        api().get(`/material/${idmaterial}/inventarioslast/`)
            .then(res => {
                this.setState({rows: _.mapKeys(res.data,'idInventario')})
            }).catch(e => {
                this.setState({ ...this.state, errorResponse: e.response});
            });
    }

    renderMaterial() {
        var callbackValue = (value) => {
            if(value.idmaterial) {
                this.fetchInventario(value.idmaterial);
            }
        }
        return (<div className="form-row">
                    <div className="col-md-12 mb-3">
                        <SelMaterial label="Buscar" 
                                        value={callbackValue}
                                        error="Se debe ingresar un valor"
                                        ok="Ok"/>
                    </div>
                </div>
        );
    }

    renderLista() {
        if(!this.state.rows){
            return null;
        }
        return (<div className="list-group">
                    {this.renderContent()}
                </div>);
    }

    render() {
        return (<div className="container">
                    <h1>Despacho</h1>
                    <Msg msg={this.state.msg} status={this.state.status}/>
                    {this.renderMaterial()}
                    <form className="col-lg-6">
                        {this.renderLista()}
                        <button className="btn btn-primary"
                            onClick={e=>this.onSubmit()}>Guardar</button>
                    </form>
                </div>);
    }
}

const mapStateToProps = state => {
    return {items: state.api.items}
}


export default Despacho;