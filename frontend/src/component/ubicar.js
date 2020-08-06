import React, { Component } from 'react';
import api from '../util/api';  
import AlmacenTreeView from './elements/almacenTreeView';
import { connect } from 'react-redux';
import almacen from './almacen';

class Ubicar extends Component {

    constructor(props) {
        super(props);
        this.almacenTreeView = React.createRef();
    }

    state = {posSel: '', ors: null, errorResponse: null, idoritem: null};

    componentDidMount() {
        this.fetchOr();
        this.modal = window.$('.modal');
    }

    fetchOr() {
        api().get('/ors')
        .then(res => {
            this.setState({ ...this.state, ors: res.data});
        }).catch(e => {
            this.setState({ ...this.state, errorResponse: e.response});
        })
    }

    onClickList(e, o) {
        this.setState({ ...this.state, idoritem: o.idoritem});
        var msg = `¿Confirma que material ${o.material.nombre} con id 
                    ${o.idoritem} fue almacenado en 
                    la posición ${this.state.posSel.ubicacion}?`
        this.modal.find('.modal-body').text(msg);
        this.modal.modal('show');
    }

    renderList() {
        if(!this.state.ors) {
            return null;
        }
        var clase = 'list-group-item list-group-item-action';
        if(this.state.posSel=='') {
            clase=clase.concat(' disabled');
        }
        return this.state.ors.map(o => {
            return (<a key={o.idoritem} href="#" class={clase} onClick={e => this.onClickList(e,o)}>
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">{o.material.nombre}</h5>
                            <small>id: {o.idoritem}</small>
                        </div>
                        <p class="mb-1">Cantidad: {o.cantidad}</p>
                        <small>Donec id elit non mi porta.</small>
                    </a>)
        });
    }

    sendUbicacion() {
        console.log(this.state.posSel);
        api().patch('/inventarios',
            { idoritem: this.state.idoritem,
            idpos: this.state.posSel.id})
        .then(res => {
            this.setState({ ...this.state, ors: res.data});
        })
    }
    
    renderConfirmacion() {
        return (<div className="modal fade" tabIndex="-1" role="dialog">
                    <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                        <h5 className="modal-title">Confirmación</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        </div>
                        <div className="modal-body">
                        <p></p>
                        </div>
                        <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" className="btn btn-primary" data-dismiss="modal"
                                onClick={e => this.sendUbicacion()}>Guardar</button>
                        </div>
                    </div>
                    </div>
                </div>)
    }
    
    onClickAlmacen = (ubicaciones, idNode) => {
        var alpha = idNode.replace(/[^a-zA-Z]/gi,'');
        var num = parseInt(idNode.replace(/[^0-9]/gi,''));
        if(alpha==='idAlmacen')
          this.setState({valueNombre: this.props.almacen,
            nameNombre:idNode})
        else {
          var pos = this.almacenTreeView.current.findField(ubicaciones, num, alpha);
          if(pos!= null && !pos.hasOwnProperty('niveles') && !pos.hasOwnProperty('poses')){
            this.setState({ ...this.state, posSel: pos });
          }          
        }
    }

    render() {
        return (<div className="container" style={{zIndex:-10}}>
                {this.renderConfirmacion()}
                <div className="row">
                    <h1>Ubicar Recepción</h1>
                </div>
                <div className="row" style={{ justifyContent: 'center'}}>
                    <h2 style={{marginRight:'4em'}}> Posición Seleccionada: {this.state.posSel.ubicacion}</h2>
                </div>
                <div className="row">
                    <div className="col-sm-5">
                    <AlmacenTreeView ref={this.almacenTreeView}
                        onClick={this.onClickAlmacen}
                        Almacen={this.props.almacen}
                        />
                    </div>
                    <div className="col-sm-6 mt-7">
                    <div className="list-group">
                        {this.renderList()}
                    </div>
                    </div>
                </div>
                </div>);
    }
}

const mapStateToProps = state => {
    return {almacen: state.api.almacen,}
}

export default connect(mapStateToProps, {})(Ubicar);