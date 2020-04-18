import React, { Component } from 'react';
import { connect }  from 'react-redux';
import AlmacenTreeView from './elements/almacenTreeView';
import {listarAlmacen, updateZonas, submitZonas} from '../actions/';

class almacen extends Component {

    state = { seleccionado: '', valueNombre:'', nameNombre:''}

    componentDidMount() {
        this.props.listarAlmacen();
    }

    onChange(e) {
        var alpha = this.state.nameNombre.replace(/[^a-zA-Z]/gi,'');
        var num = parseInt(this.state.nameNombre.replace(/[^0-9]/gi,''));
        var resp = this.editField(this.props.zonas, num, alpha, e.target.value)
        this.props.updateZonas(resp);
        this.setState({valueNombre: e.target.value});
    }

    onSubmit(e) {
        e.preventDefault();
        this.props.submitZonas(this.props.zonas);
    }

    renderForm() {
        return (
            <form>
                <div className="form-group mx-3">
                    <label htmlFor="idNombre">Nombre</label>
                    <input
                        id="idNombre" autoComplete="off"
                        nameNombre={this.state.nameNombre}
                        className="form-control form-control-lg mb-3"
                        // onKeyDown={e => }
                        onChange={e => this.onChange(e)}
                        value={this.state.valueNombre}
                    >
                    </input>
                    <button type="submit" onClick={e => this.onSubmit(e)}
                        className="btn btn-primary">
                        Guardar
                    </button>
                </div>
            </form>
        );
    }

    findField(element, id, fieldTarget) {
        // Extrae el campo child del objecto element según corresponde
        const fields = ['zonas', 'niveles', 'poses'];
        var field =  fields
                        .find(i => !!Object.keys(element[0])
                            .find(e => i===e));
        var NField = fields.indexOf(field);
        if(NField===-1) NField=3;
        var NFieldTarget = fields.indexOf(fieldTarget);
        return element
            .reduce((a,c) => {
              // En caso de encontrarlo sale.
                if(!!a) return a;
                // En caso de estar en el nivel del árbol correcto,
                // revisa si corresponde el id.
                if(c.id===id && NField===(NFieldTarget+1)) return c;
                // Hace un llamado recursivo un nivel más bajo en el árbol.
                if(typeof c[field] !== 'undefined') {
                    if(c[field].length > 0){
                        var children = this.findField(c[field],id,fieldTarget);
                        if(!!children) return children;
                        else return null;
                    }
                }
                return null;
            },null);
    }

    editField(element, id, fieldTarget, value) {
        // Extrae el campo child del objecto element según corresponde
        const fields = ['zonas', 'niveles', 'poses'];
        var field =  fields
                        .find(i => !!Object.keys(element[0])
                            .find(e => i===e));
        var NField = fields.indexOf(field);
        if(NField===-1) NField=3;
        var NFieldTarget = fields.indexOf(fieldTarget);
        return element
            .map(e => {
                // En caso de estar en el nivel del árbol correcto,
                // revisa si corresponde el id.
                if(e.id===id && NField===(NFieldTarget+1)) {
                    e.nombre = value;
                }
                // Hace un llamado recursivo un nivel más bajo en el árbol.
                if(typeof e[field] !== 'undefined') {
                    if(e[field].length > 0){
                        var children = this.editField(e[field],id,fieldTarget,value);
                    }
                }
                return e;
            });
    }

    onClick = (idNode) => {
        var alpha = idNode.replace(/[^a-zA-Z]/gi,'');
        var num = parseInt(idNode.replace(/[^0-9]/gi,''));
        if(alpha==='idAlmacen')
          this.setState({valueNombre: this.props.almacen, nameNombre:idNode})
        else {
          var campo = this.findField(this.props.zonas, num, alpha);
          this.setState({valueNombre: campo.nombre, nameNombre:idNode});
        }
    }

    render() {
        return (
                <div className="container">
                    <h1>Almacen</h1>
                    <div className="row">
                    <div className="col-sm-5">
                        <AlmacenTreeView
                        onClick={this.onClick}
                        Almacen={this.props.almacen}
                        Zonas={this.props.zonas}
                        />
                    </div>
                    <div className="col-sm-4">
                        {this.renderForm()}
                    </div>
                    </div>
                </div>
            )
    }
}

const mapStateToProps = (state) => {
    return {
      zonas: state.api.ubicaciones, 
      almacen: state.api.almacen
    }
}


export default connect(mapStateToProps, {listarAlmacen, updateZonas, submitZonas })(almacen);