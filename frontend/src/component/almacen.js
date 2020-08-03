import React, { Component } from 'react';
import { connect } from 'react-redux';
import AlmacenTreeView from './elements/almacenTreeView';

class Almacen extends Component {

    constructor(props) {
        super(props);
        this.almacenTreeView = React.createRef();
    }

    state = { seleccionado: '', valueNombre:'', nameNombre:''}

    onChange(e) {
        var alpha = this.state.nameNombre.replace(/[^a-zA-Z]/gi,'');
        var num = parseInt(this.state.nameNombre.replace(/[^0-9]/gi,''));
        var resp = this.almacenTreeView.current.editField(this.props.ubicaciones, num, alpha, e.target.value)
        this.setState({valueNombre: e.target.value});
    }

    onSubmit(e) {
        e.preventDefault();
        this.props.submitZonas(this.props.ubicaciones);
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
    
    onClick = (ubicaciones, idNode) => {
        var alpha = idNode.replace(/[^a-zA-Z]/gi,'');
        var num = parseInt(idNode.replace(/[^0-9]/gi,''));
        if(alpha==='idAlmacen')
          this.setState({valueNombre: this.props.almacen, nameNombre:idNode})
        else {
          var campo = this.almacenTreeView.current.findField(ubicaciones, num, alpha);
          this.setState({valueNombre: campo.nombre, nameNombre:idNode});
        }
    }

    render() {
        return (<div className="container">
                    <h1>Almacen</h1>
                    <div className="row">
                    <div className="col-sm-5">
                        <AlmacenTreeView ref={this.almacenTreeView}
                        onClick={this.onClick}
                        Almacen={this.props.almacen}
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
      almacen: state.api.almacen
    }
}


export default connect(mapStateToProps, {})(Almacen);