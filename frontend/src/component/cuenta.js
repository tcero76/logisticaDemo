import React, { Component} from 'react';
import { IntlProvider, FormattedDate } from 'react-intl';
import IntlData from '../util/intlData';
import Msg from './elements/msg';
import api from '../util/api';

class Cuenta extends Component {

    state = {hideMsg: true, cuentas: null, errorResponse: '', zonas: null }

    componentDidMount() {
        this.fetchCuentas();
        this.fetchZonas();
    }

    saveCuenta(idzona) {
        api().post('/cuentas', { idzona: idzona })
            .then(res => {
                this.setState({ ...this.state, cuentas: res.data})                
            }).catch(e => {
                this.setState({ ...this.state, errorResponse: e.response });
            })
    }

    fetchZonas() {
        api().get('/zona')
        .then(res => {
            this.setState({ zonas: res.data});
        }).catch(e => {
            this.setState({...this.state, errorResponse: e.response});
        });
    }

    fetchCuentas() {
        api().get('/cuentas')
            .then(res => {
                this.setState({ ...this.state, cuentas: res.data });
            }).catch(res => {
                this.setState({ ...this.state, errorResponse: res.response });
            })
    }

    renderRow() {
        return this.state.cuentas.map(c => {
            return (
                    <tr key={c.idcuenta}>
                        <td>{c.idcuenta}</td>
                        <td><FormattedDate value={c.fecharegistro} format="short"/></td>
                        <td>{c.nombre}</td>
                        <td>{c.status}</td>
                    </tr>
            )
        })
        
    }

    renderTabla() {
        if(!this.state.cuentas) {
            return null;
        }
        return <table className="table">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>fecha</th>
                            <th>Zona</th>
                            <th>Status</th>
                        </tr>
                    </thead>      
                    <tbody>
                        {this.renderRow()}   
                    </tbody>  
                </table>
    }

    selectZona() {
        if(!this.state.zonas){
            return null;
        }
        return (
            this.state.zonas.map((z,i) => {
                return (
                <option key={z.idzona} value={z.idzona}>{z.nombre}</option>
                );
            })
        );
    }
    
    onClickModal(e) {
        this.saveCuenta(this.zona);
    }

    renderModal() {
        return (<div className="modal fade" id="NvaCuenta" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div className="modal-dialog" role="document">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="exampleModalLabel">Modal title</h5>
              <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div className="modal-body">
            <select id="slcZona" className="form-control form-control-lg"
                onChange={e => this.zona = e.target.value}>
                {this.selectZona()}
            </select>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
              <button type="button" className="btn btn-primary" data-dismiss="modal" onClick={e => this.onClickModal(e)}>Save changes</button>
            </div>
          </div>
        </div>
      </div>);
    }

    render() {
        return (<div className="container">
            <IntlProvider locale="es" {...IntlData}>
                <h1 className="mb-3">Conteo de Inventario</h1>
                <Msg Hide={this.state.hideMsg}/>
                <button className="btn btn-primary mb-2" onClick={e => this.setState({hideMsg:true})} data-toggle="modal" data-target="#NvaCuenta">
                    <svg className="bi bi-plus-circle-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fillRule="evenodd" d="M16 8A8 8 0 110 8a8 8 0 0116 0zM8.5 4a.5.5 0 00-1 0v3.5H4a.5.5 0 000 1h3.5V12a.5.5 0 001 0V8.5H12a.5.5 0 000-1H8.5V4z" clipRule="evenodd"/>
                    </svg>
                </button>
                    {this.renderTabla()}
                    {this.renderModal()}
                </IntlProvider>
        </div>)
    }
}

export default Cuenta;