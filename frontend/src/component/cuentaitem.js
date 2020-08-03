import React , { Component } from 'react';
import NumberFormat from 'react-number-format';
import SelMaterial from './elements/selMaterial';
import { IntlProvider, FormattedDate, FormattedMessage } from 'react-intl';
import IntlData from '../util/intlData';
import locale from '../util/locale';
import Tabla from './elements/tabla';
import api from '../util/api';

class CuenatItem extends Component {

    state = {selCuenta: null,
            activePos: true,
            listPos: [],
            touchCantidad: false,
            touchPos: false,
            touchNivel:false,
            cuenta: [],
            cuentaitem: null,
            errorResponse: '',
            zonaDetalle:null,
            msn: null,
        }

    componentDidMount() {
        api().get('/cuentas')
            .then(res => {
                this.setState({ ...this.state, cuentas: res.data });
            }).catch(res => {
                this.setState({ ...this.state, errorResponse: res.response });
            })
        var toast = document.getElementById('idtoast');
        window.$('.toast')
    }

    renderToast() {
        return (<div className="toast toast__panel" role="alert" aria-live="assertive" aria-atomic="true">
        <div className="toast-header">
          <strong className="mr-auto">Respuesta</strong>
          <button type="button" className="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div className="toast-body">
          {this.state.msn}
        </div>
      </div>)
    }

    renderHeaders() {
        return (<tr>
                    <th>Id</th>
                    <th>Material</th>
                    <th>Cantidad</th>
                    <th>Usuario</th>
                    <th>Ubicación</th>
                </tr>)
    }

    renderRow = () => {
        if(!this.state.cuentaitem){
            return null;
        }
        return this.state.cuentaitem.list.map(ci => {
            return(<tr key={ci.idcuentaitem}>
                <td>{ci.idcuentaitem}</td>
                <td>{ci.nombre}</td>
                <td>{ci.cantidad}</td>
                <td>{ci.usuarionombre}</td>
                <td>{ci.ubicacion}</td>
            </tr>)
        })
    }

    onClickCuenta(idcuenta) {
        var resp = this.state.cuentas.find(c => c.idcuenta === idcuenta);
        this.setState({selCuenta: idcuenta});
        this.fetchZonaDetail(resp.idzona);
        this.page = 1;
        this.fetchCuentaItem({idcuenta, page:this.page});
    }

    fetchZonaDetail(idzona) {
        api().get(`/zona/${idzona}`)
            .then(res => {
                this.setState({ ...this.state, zonaDetalle: res.data });
            })
            .catch(e => {
                this.setState({ ...this.state, errorResponse: e.response});
            });
    }

    fetchCuentaItem({idcuenta, page}) {
        api().get(`/cuentas/${idcuenta}?page=${page}&rows=4`)
        .then(res => {
            this.setState({ ...this.state, cuentaitem: res.data});
        }).catch(e => {
            this.setState({ ...this.state, errorResponse: e.response});
        });;
    }

    renderTable() {
        if(!this.state.selCuenta || !this.state.cuentaitem) {
            return null;
        }
        return (<Tabla rows={this.state.cuentaitem} page={this.state.cuentaitem.page}
                    totalPage={this.state.cuentaitem.totalPage}
                    renderHeaders={this.renderHeaders()}
                    fetchData={page => this.fetchCuentaItem({idcuenta:this.state.selCuenta, page})}
                    renderRows={this.renderRow}/>);
    }

    renderMaterial() {
        var callbackValue = (value) => {
            this.material = value;
        }
        return <SelMaterial
                    label="Buscar Material"
                    value={callbackValue}
                    error="Se debe ingresar un valor"
                    ok="Ok"
                />
    }

    renderCuenta() {
        if(!this.state.cuentas) {
            return null;
        }
        return (
            this.state.cuentas.map(c => {
                return (
                    <div className="list-group" key={c.idcuenta}>
                        <button type="button" onClick={e => this.onClickCuenta(c.idcuenta)}
                            className="list-group-item list-group-item-action">
                                Zona: <strong>{c.nombre}</strong>, Solicitado: <strong><FormattedDate value={c.fecharegistro} format="short"/></strong>
                        </button>
                    </div>
                )
            })
        );
    }

    onChangeNivel(e) {
        if(e.target.value!=='Seleccionar') {
            this.nivel = this.state.zonaDetalle.niveles
                            .find(n => n.idnivel==e.target.value);
            this.setState({
                activePos: false,
                listPos: this.nivel.poses,
            })
        }
    }

    onClickGuardar(e){
        e.preventDefault();
        api().post('/cuentaitem', {
            idcuenta: this.state.selCuenta,
            cantidad: this.cantidad,
            idmaterial: this.material.idmaterial,
            idpos: this.pos.idpos
        }).then(res => {
                this.setState({cuentaitem: res.data});
            }).catch(res => {
                this.setState({errorResponse: res.response})
            })
        if(this.state.msn) {
            window.$('.toast').toast({delay: 5000}).toast('show');
        }
    }

    renderForm() {
        if(!this.state.zonaDetalle) {
            return null;
        }
        var classCantidad = "form-control mb-2";
        var classNivel = "form-control mb-2";
        var classPos = "form-control mb-2";
        if(this.state.touchCantidad && !this.cantidad) {
            classCantidad = "form-control mb-2 is-invalid";
        } else if(this.state.touchCantidad && this.cantidad) {
            classCantidad = "form-control mb-2 is-valid";
        }
        if(this.state.touchNivel && !this.nivel) {
            classNivel = "form-control mb-2 is-invalid";
        } else if(this.state.touchNivel && this.nivel) {
            classNivel = "form-control mb-2 is-valid";
        }
        if(this.state.touchPos && !this.pos) {
            classPos = "form-control mb-2 is-invalid"
        } else if(this.state.touchPos && this.pos) {
            classPos = "form-control mb-2 is-valid";
        }
        return (
            <form>
                {this.renderMaterial()}
                <label htmlFor="idCantidad">Cantidad</label>
                <NumberFormat className={classCantidad}
                    id="idCantidad"
                    autoComplete="off"
                    onChange={e => this.cantidad=e.target.value}
                    onBlur={e => this.setState({touchCantidad: true})}
                    >
                </NumberFormat>
                <div className="invalid-feedback">
                    Se debe indicar la cantidad.
                </div>
                <div className="row mb-2">
                    <div className="col-3">
                        <label htmlFor="slcNivel">Nivel</label>
                        <select id="slcNivel" className={classNivel}
                            onChange={e => this.onChangeNivel(e)}
                            onBlur={e => this.setState({touchNivel: true})}
                            >
                            <option value={null}>Seleccionar</option>
                            {this.state.zonaDetalle.niveles.map(n => {
                                return (<option key={n.idnivel} className="dropdown-item" href="#">{n.nombre}</option>);
                            })} 
                        </select>
                        <div className="invalid-feedback">
                            Se debe seleccionar un Nivel.
                        </div>
                    </div>
                    <div className="col-3">
                        <label htmlFor="slcPos">Posición</label>
                        <select id="slcPos" className={classPos}
                            onChange={e => this.pos = this.state.listPos.find(p => p.idpos===parseInt(e.target.value))}
                            onBlur={e => this.setState({touchPos: true})}
                            disabled={this.state.activePos}>
                            <option value={null}>Seleccionar</option>
                            {this.state.listPos.map(p => {
                                return (<option key={p.idpos} value={p.idpos} className="dropdown-item" href="#">{p.nombre}</option>);
                            })}
                        </select>
                        <div className="invalid-feedback">
                            Se debe seleccionar una posición.
                        </div>
                    </div>
                </div>
                <button className="btn btn-primary" onClick={e => this.onClickGuardar(e)}>Guardar</button>
            </form>
        );
    }

    onClickBack() {
        this.setState({selCuenta:null});
        this.fetchCuentaItem(0,0);
    }

    renderPanel() {
        if(this.state.selCuenta) {
            var zona = this.state.cuentas.find(c => c.idcuenta === this.state.selCuenta)
            return (
                <div className="card mb-3">
                        <div className="card-header">
                            <a href="#" onClick={e => this.onClickBack()}>
                                <i className="fas fa-arrow-left"></i>
                            </a>
                        <span className="mx-2">Recuento Zona: <strong>
                            {zona.nombre}
                            </strong>
                        </span>
                        </div>
                    <div className="card-body">
                        {this.renderForm()}
                    </div>
                </div>
            );
        }
        return (
            <div className="card mb-3">
                <div className="card-body">
                        {this.renderCuenta()}
                </div>
            </div>
        );
    }

    render() {
        return (
            <div className="container">
                <IntlProvider locale={locale}
                    formats={IntlData.formats}
                    messages={IntlData.messages[locale]}>
                    <h1>Cuenta de Artículos</h1>
                    {this.renderToast()}
                    {this.renderPanel()}
                    {this.renderTable()}
                </IntlProvider>
            </div>
            );
    }
}


export default CuenatItem;