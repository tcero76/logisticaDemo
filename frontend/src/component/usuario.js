import React, { Component } from 'react';
import { listUsuario } from '../actions';
import {connect} from 'react-redux';
import api from '../util/api';
import _ from 'lodash';

class Usuario extends Component {

    componentDidMount() {
        this.fetchUsuario();
    }

    state = { idusuario: null, usuarios: null}

    fetchUsuario() {
        api().get('/usuarios')
        .then(res => {
            this.setState({ ...this.state, usuarios: res.data});
        })
    }

    renderRow() {
        if(!this.state.usuarios){
            return null;
        }
        return this.state.usuarios.map(u => {
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

export default Usuario;