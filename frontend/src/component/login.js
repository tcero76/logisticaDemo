import React from 'react';
import { connect } from 'react-redux';
import { signin, currentUser } from '../actions';

class Login extends React.Component {

    state = { usuario: '', clave: '' }

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
    }

    render() {
        var error_msg = <div></div>;
        if(this.props.signin_error) {
            error_msg = (
            <div className="alert alert-danger" role="alert">
                {this.props.signin_error}
            </div>);
        }
        return (
            <div className="form__signin">
                {error_msg}
                <form>
                    <h1 className="h3 mb-3 pt-3 font-weight-bolder">Ingrese Usuario</h1>
                    <div className="mb-3">
                        <label htmlFor="usuario" className="sr-only">Usuario</label>
                        <input type="text" autoComplete="off"
                            value={this.state.usuario}
                            onChange={e => this.setState({ ...this.state, usuario: e.target.value })}
                            name="usuario" id="usuario" className="form-control"
                            placeholder="Usuario" onKeyPress={this.onEnter}
                            required autoFocus></input>
                        <label htmlFor="clave" className="sr-only">Clave</label>
                        <input type="password" autoComplete="off"
                            value={this.state.clave}
                            onChange={e => this.setState({ ...this.state, clave: e.target.value })}
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
    return {
        signin_error: state.api.signin_error,
    }
}

export default connect(mapStateToProps, { signin, currentUser })(Login);