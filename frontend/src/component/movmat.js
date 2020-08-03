import React, { Component } from 'react';
import {FormattedTime, FormattedNumber} from 'react-intl';
import Tabla from './elements/tabla';
import SelMaterial from './elements/selMaterial';
import api from '../util/api';

class Movmat extends Component {

    constructor(props) {
        super(props);
        this.materialElement = React.createRef();
    }

    state = {errorResponse: null, rows: null, page: null, totalPage: null, material:null }

    componentDidMount() {
        this.materialElement.current.resetMaterial();
    }

    SubmitMovMat(idmaterial, page) {
        if(this.state.material) {
            api().get(`/material/${idmaterial}/inventarios?page=${page}&rows=8`)
                .then(res => {
                    this.setState({rows: res.data.list, page: res.data.page, totalPage: res.data.totalPage})
                }).catch(e => {
                    this.setState({errorResponse:e.response})
                })
        }
    }

    renderMaterial() {
        var callbackValue = (value) => {
            this.setState({...this.state, material:value});
            this.SubmitMovMat(value.idmaterial,1);
        }
        return (
            <div className="form-row">
                <div className="col-md-12 mb-3">
                    <SelMaterial label="Seleccionar"
                        ref={this.materialElement}
                        value={callbackValue}
                        error="Se debe ingresar un valor"
                        ok="Ok"/>
                </div>
            </div>
        );
    }

    renderHeaders() {
        return ( <tr>
                    <th>id</th>
                    <th>Material</th>
                    <th>Cantidad</th>
                    <th>Ubicación</th>
                    <th>Fecha</th>
                    <th>Total</th>
                </tr>)
    }

    renderRows = () => {
        var rows = this.state.rows;
        return rows.map(m => {
                return (<tr key={m.idinventario}>
                            <td>{m.idinventario}</td>
                            <td>{m.nombre}</td>
                            <td><FormattedNumber value={m.cantidad}></FormattedNumber> {m.positivo?<i class="fas fa-arrow-circle-up" style={{color:'blue'}}></i>:<i class="fas fa-arrow-circle-down" style={{color:'red'}}></i>}</td>
                            <td>{m.pos}</td>
                            <td><FormattedTime format="hhmm" value={m.fecharegistro}></FormattedTime></td>
                            <td><b>{m.total}</b></td>
                        </tr>);
            });
    }

    render() {
        return (<div className="container">
                    <div className="row mb-3">
                        <div className="col-md-5">
                            <div className="card card__movmat">
                                <div className="card-header">
                                    Buscar
                                </div>
                                <div className="card-body">
                                    <form>
                                        {this.renderMaterial()}
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <Tabla rows={this.state.rows}
                        totalPage={this.state.totalPage}
                        renderHeaders={this.renderHeaders()}
                        renderRows={this.renderRows}
                        fetchData={(page) => this.SubmitMovMat(this.state.material.idmaterial,page)}/>
                </div>);
    }
}

export default Movmat;