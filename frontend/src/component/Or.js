import React from 'react';
import { IntlProvider, FormattedNumber } from 'react-intl';
import intlData from '../util/intlData';
import NumberFormat from 'react-number-format';
import SelMaterial from './elements/selMaterial';
import Msg from './elements/msg';
import api from '../util/api';
import _ from 'lodash';

class Or extends React.Component {

    constructor(props) {
        super(props);
        this.materialElement = React.createRef();
    }

    componentDidMount() {
    }

    state = { guiadedespacho: '', cantidad: null, material: null, oritems: {},
        alert:false,
        touched:{cantidad: false, guiadedespacho: false},
        error: {cantidad: null, guiadedespacho: null},
        msg: null,status: null, HideMsg: true
        }

    saveOrec() {
        var oritems = Object.values(this.state.oritems);
        api().post('/ors', {
            guiadespacho: this.state.guiadespacho,
            oritems: oritems.map(oi =>{
                return {
                    idmaterial: oi.material.idmaterial,
                    cantidad: oi.cantidad,
                    pos: oi.pos,
                }
            })
        })
        .then(res => {
            this.setState({ ...this.state, oritems: res.data,
            msg: "Or almacenda con id: " + res.data, status: res.status });
        }).catch(e => {
            this.setState({ ...this.state, msg: e.response});
        })
    }
    
    renderTableRow() {
        var oritems = Object.values(this.state.oritems);
        return oritems.sort((oi1,oi2) => oi1.pos-oi2.pos).map(fila => {
            return (
                <tr key={fila.material.idmaterial}>
                    <td>{fila.material.nombre}</td>
                    <td>{fila.pos}</td>
                    <td>
                        <FormattedNumber value={fila.cantidad} />
                    </td>
                </tr>
            );
        })
    }

    onSubmitOrec () {
        this.saveOrec({ oritems: Object.values(this.state.oritems),
            guiadespacho: this.state.guiadedespacho });
        this.setState({ guiadedespacho: '', touched:{cantidad: false, guiadedespacho: false},
        error: {cantidad: null, guiadedespacho: null}, oritems: [], alert: true});
    }

    renderTable() {
        if(Object.values(this.state.oritems).length===0) {
            return null;
        }
        return (
            <div className="table-responsive">
                <table className="table">
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>pos</th>
                            <th>Cantidad</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderTableRow()}
                    </tbody>
                </table>
                <button className="btn btn-primary" onClick={e => this.onSubmitOrec()}>Guardar</button>
            </div>
        )
    }

    renderMaterial() {
        var callbackValue = value => {
            this.setState({ ...this.state, material: value})
        }
        return (<div className="col-md-6 mb-3">
                    <SelMaterial ref={this.materialElement}
                        label="Material"
                        value={callbackValue}
                        error="Se debe ingresar un valor"
                        ok="Ok"/>
                </div>
        );
    }

    onChangeCantidad(e) {
        this.setState({ ...this.state, cantidad: parseFloat(e.target.value.replace(',','.'))});
        this.testError(e);
    }

    onBlurCantidad(e) {
        this.setState({ ...this.state, touched: { ...this.state.touched,cantidad:true}});
        this.testError(e)
    }

    testError(e) {
        if(e.target.value=='' || e.target.value==null) {
            this.state.error['cantidad'] = 'Se debe ingresar un valor';
        } else {
            this.state.error['cantidad'] = null;
        }
    }

    renderCantidad () {
        var classNam='form-control';
        if(this.state.touched.cantidad) {
            classNam = `form-control ${!!this.state.error['cantidad'] ? ' is-invalid' : ' is-valid'}`;
        }
        return (
            <div className="col-md-3 mb-3">
                <label htmlFor="cantidad">Cantidad</label>
                <NumberFormat id="cantidad"
                    onChange={e => this.onChangeCantidad(e)}
                    onBlur={e => this.onBlurCantidad(e)}
                    value={this.state.cantidad}
                    className={classNam}
                    autoComplete="off"
                    thousandSeparator="."
                    decimalSeparator=","/>
                    <div className="invalid-feedback">
                        Se debe ingresar un valor
                    </div>
                    <div className="valid-feedback">
                        Ok.
                    </div>
            </div>
        );
    }

    onClickAgregar(e) {
        e.preventDefault();
        if(this.state.cantidad && this.state.material.idmaterial!=null) {
            var keySet = _.mapKeys([{
                                        cantidad: parseFloat(this.state.cantidad),
                                        material: this.state.material,
                                    }],
                                'material.idmaterial');
            // var oi = this.state.oritems.find(o => o.material.idmaterial === this.state.material.idmaterial)
            if(typeof this.state.oritems[this.state.material.idmaterial] ==='undefined'){
                var oritems = {...this.state.oritems, ...keySet};
                oritems[this.state.material.idmaterial]['pos']=Object.keys(oritems).length;
            } else {
                var pos = this.state.oritems[this.state.material.idmaterial]['pos'];
                keySet[this.state.material.idmaterial]['pos']=pos;
                var oritems = {...this.state.oritems, ...keySet};
            }


            this.setState({ oritems, cantidad: null,
                    touched: { ...this.state.touched, cantidad:false} });
            this.materialElement.current.resetMaterial();
        }
    }

    renderFormRow() {
        return (
            <div className="form-row mb-2 form__fila">
                {this.renderMaterial()}
                {this.renderCantidad()}
                <div className="col-md-2 mb-3" style={{top:'2rem'}}>
                    <button className="btn btn-primary"
                        onClick={e => this.onClickAgregar(e)}>Agregar</button>
                </div>
                
            </div>
        )
    }

    onChangeGuiadedespacho(e) {
        this.setState({ ...this.state, guiadedespacho: e.target.value});
        this.testErrorGuiadedespacho(e);
    }

    onBlurGuiadedespacho(e) {
        this.setState({ ...this.state, touched: { ...this.state.touched, guiadedespacho:true}});
        this.testErrorGuiadedespacho(e);
    }

    testErrorGuiadedespacho(e) {
    if(!e.target.value) {
        this.state.error['guiadedespacho'] = 'Se debe ingresar un valor';
    } else {
        this.state.error['guiadedespacho'] = null;
    }
    }

    renderGuia() {
        var classNam = 'form-control';
        if(this.state.touched['guiadedespacho']) {
            classNam = `form-control ${this.state.error['guiadedespacho']?' is-invalid':'is-valid'}`;
        }
        return (
            <div className="col-md-4">
                <label htmlFor="guiadespacho">Guía de Despacho</label>
                <input id="guiadespacho"
                    onChange={e => this.onChangeGuiadedespacho(e)}
                    onBlur={e => this.onBlurGuiadedespacho(e)}
                    value={this.state.guiadedespacho}
                    name="guiadespacho"
                    className={classNam}
                    autoComplete="off"></input>
                <div className="invalid-feedback">Se debe ingresar el código</div>
                <div className="valid-feedback">Ok</div>
            </div>
        )
    }

    renderFormGuia() {
        return (
            <div className="form-row form__fila">
                {this.renderGuia()}
            </div>
        )
    }

    renderAlert() {
        if(!this.state.alert) {
            return null;
        }
        return (
            <div className="alert alert-success" role="alert">
                idOrec guardada
            </div>
        )
    }

    renderForm() {
        return (
            <form>
                <Msg/>
                {this.renderFormGuia()}
                {this.renderFormRow()}
            </form>
        );
    }

    render() {
        return (
            <IntlProvider locale={navigator.language} {...intlData}>
                <div className="container">
                    <h1 className="mb-3">Recepción Ors</h1>
                    <Msg msg={this.state.msg}
                        status={this.state.status} 
                        HideMsg={this.state.HideMsg}/>
                    <div className="card mb-4 card__component">
                        <div className="card-body">
                            {this.renderForm()}
                        </div>
                    </div>
                    <div className="table__container">
                        {this.renderTable()}
                    </div>
                </div>
            </IntlProvider>
        );
    }
}

export default Or;