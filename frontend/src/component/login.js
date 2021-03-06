import React from 'react';
import { connect } from 'react-redux';
import { signin, currentUser } from '../actions';
import Msg from './elements/msg';

class Login extends React.Component {

    state = { usuario: '', clave: '', HideMsg: true }

    componentDidMount() {
    }

    onEnter = e => {
        if(e.key==='Enter'){
            this.sign_in();
        }
    }

    sign_in = e => {
        this.props.signin({
            usuario: this.state.usuario,
            clave: this.state.clave,
        });
        this.setState({ ...this.state, HideMsg: false})
    }

    render() {
        return (
            <div className="form__signin">
                <Msg msg={this.props.msg} Hide={this.state.HideMsg}
                    status={this.props.status}/>
                <form>
                    <h1 className="h3 mb-3 pt-3 font-weight-bolder">Ingrese Usuario</h1>
                    <div className="mb-3">
                        <label htmlFor="usuario" className="sr-only">Usuario</label>
                        <input type="text" autoComplete="off"
                            value={this.state.usuario}
                            onChange={e => this.setState({ ...this.state, usuario: e.target.value, HideMsg:true })}
                            name="usuario" id="usuario"
                            className="form-control mb-1"
                            placeholder="Usuario"
                            onKeyPress={this.onEnter}
                            required autoFocus></input>
                        <label htmlFor="clave" className="sr-only">Clave</label>
                        <input type="password" autoComplete="off"
                            value={this.state.clave}
                            onChange={e => this.setState({ ...this.state, clave: e.target.value, HideMsg:true })}
                            name="clave" id="clave" className="form-control"
                            placeholder="Clave" onKeyPress={this.onEnter}
                            required></input>
                    </div>
                </form>
                <button onClick={this.sign_in}
                    className="btn btn-lg btn-primary btn-block">
                    Sign in
                </button>
            </div>
        );
    }

}

const mapStateToProps = state => {
    return { msg: state.api.msg, status: state.api.status }
}

export default connect(mapStateToProps, { signin, currentUser })(Login);