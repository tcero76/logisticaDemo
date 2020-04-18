import React, { Component } from 'react';
import { listUsuario } from '../actions';
import {connect} from 'react-redux';

class usuario extends Component {

    componentDidMount() {
        this.props.listUsuario();
    }

    state = { idusuario: null}

    renderRow() {
        if(!this.props.listadoUsuario){
            return null;
        }
        return this.props.listadoUsuario.map(u => {
            return (<tr key={u.idUsuario}>
                    <td>{u.idUsuario}</td>
                    <td>{u.nombre}</td>
                    <td>{u.rol}</td>
            </tr>)
        });
    }

    renderTable() {
        return (<table className="table mt-4">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Nombre</th>
                            <th>Rol</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderRow()}
                    </tbody>
                </table>)
    }

    render() {
        return <div className="container">
                    <h1>Usuarios</h1>
                    {this.renderTable()}
                </div>
    }
}

const mapStateToProps = state => {
    console.log(state.api.listadoUsuario)
    return {listadoUsuario: state.api.listadoUsuario}
}

export default connect(mapStateToProps, { listUsuario })(usuario);