import React, { Component } from 'react';
import {FormattedDate, IntlProvider} from 'react-intl';
import intlData from '../util/intlData';
import api from '../util/api';
import Msg from './elements/msg';
import { reduce } from 'lodash';

class CuentaCuadrar extends Component {

    state = {selCuenta:null, cuentas: [], cuadratura: null, errorResponse: '',
        status: '' }

    componentDidMount() {
        this.fetchCuentas();
    }

    fetchCuentas() {
        api().get('/cuentas')
            .then(res => {
                this.setState({ ...this.state, cuentas: res.data });
            }).catch(res => {
                this.setState({ ...this.state, errorResponse: res.response });
            })
    }

    fetchCuadratura(idcuenta) {
        api().get(`/cuadraturas/${idcuenta}?page=${1}`)
            .then(res => {
                this.setState({ ...this.state, cuadratura: res.data})
            }).catch(e => {
                this.setState({ ...this.state, errorResponse: e.response.data.message, status: e.response.status });
            });
    }

    onClickCorregir(c) {
        if(c.idcuenta) {
            api().patch('/cuadraturas', {idcuentaitem:c.idcuentaitem})
            .then(res => {
                this.setState({ ...this.state, cuadratura: res.data});
            })
            .catch(e => {
                this.setState({ ...this.state,
                    errorResponse: e.response.data.message, status:e.response.status });
            });
        } else if(c.idinventario) {
            api().patch('/cuentaitem', {idinventario:c.idinventario,
            idcuenta: this.state.selCuenta})
            .then(res => {
                this.setState({ ...this.state, cuadratura: res.data});
            })
            .catch(e => {
                this.setState({ ...this.state,
                    errorResponse: e.response.data.message, status:e.response.status });
            });
            
        }
    }

    renderRow() {
        if(!this.state.cuadratura) {
            return null;
        }
        return this.state.cuadratura.map((c,idx) => {
            var ci = !c.cantidadInventario?0:c.cantidadInventario;
            var cc = !c.cantidadCuenta?0:c.cantidadCuenta;
            var icon = {}
            if(cc<ci) icon = {color:'red', char: 'fas fa-thumbs-down'};
            if(cc>ci) icon = {color:'blue', char: 'fas fa-thumbs-up'};
            if(cc==ci) icon = {color:'green', char: 'fas fa-check-circle'};
            return (<tr key={idx}>
                        <td>{c.idcuentaitem}</td>
                        <td>{c.nombre}</td>
                        <td>{ci}</td>
                        <td>{cc}</td>
                        <td>{c.ubicacion}</td>
                        <td><i className={icon.char}
                            style={{color: icon.color, cursor: 'pointer'}}
                            onClick={e => this.onClickCorregir(c)}></i>
                        </td>
                    </tr> );
        })
    }

    renderTable() {
        return (<table className="table">
                    <thead>
                        <tr>
                            <th>Cuenta Item</th>
                            <th>Material</th>
                            <th>Cantidad Inventario</th>
                            <th>Cantidad Contada</th>
                            <th>Ubicación</th>
                            <th>Corregir</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderRow()}           
                    </tbody>
                </table>);
    }

    onClickCuenta(c) {
        this.setState({ ...this.state, selCuenta: c.idcuenta});
        this.fetchCuadratura(c.idcuenta)
    }

    renderCuentas() {
        if(!this.state.cuentas){
            return null;
        }
        if(this.state.cuadratura) {
            return this.renderTable()
        }
        return this.state.cuentas.map((c,idx) => {
            return (<a href="#" key={idx}
                        className="list-group-item list-group-item-action"
                        onClick={e => this.onClickCuenta(c)}
                        >
                        Zona: <strong>{c.nombre}</strong>, Solicitado: <strong>
                            <FormattedDate value={c.fecharegistro} format="short"/>
                            </strong>
                    </a>)
        });
    }

    renderContenido() {
        if(!this.state.selCuenta) {
            return (<div className="list-group">
                {this.renderCuentas()}
                </div>);
        }
        return this.renderTable();
    }
    render() {
        return (<IntlProvider locale="es" {...intlData}>
                    <div className="container">
                        <h1 className="mb-3">Cuadratura Conteo</h1>
                        <Msg msg={this.state.errorResponse}
                            status={this.state.status}/>
                        {this.renderContenido()}
                    </div>
                </IntlProvider>);
    }
}

const mapStateToProps = state => {
    return {
            cuentas: state.api.cuentas,
            cuadratura: state.api.cuadratura,
            }
}

export default CuentaCuadrar;