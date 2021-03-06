import React from 'react';
import Header from './component/header';
import { Router, Route } from 'react-router-dom';
import history from './util/history';

import Or from './component/Or';
import Ubicar from './component/ubicar';
import Login from './component/login';
import despacho from './component/despacho';
import almacen from './component/almacen';
import usuario from './component/usuario';
import movmat from './component/movmat';
import Cuenta from './component/cuenta';
import CuentaItem from './component/cuentaitem';
import CuentaCuadrar from './component/cuentacuadrar';
import StatusBar from './component/elements/statusBar'

import { connect } from 'react-redux'
import { Redirect } from 'react-router-dom'
import { currentUser, logout } from './actions/';

import './scss/style.scss';

class App extends React.Component {



  componentDidMount() {
    this.props.currentUser();
    this.events = [
      "load",
      "mousemove",
      "mousedown",
      "click",
      "scroll",
      "keypress"
    ];
    this.warn = this.warn.bind(this);
    this.logout = this.logout.bind(this);
    this.resetTimeout = this.resetTimeout.bind(this);
  }

  clearTimeout() {
    if (this.warnTimeout) clearTimeout(this.warnTimeout);
    if (this.logoutTimeout) clearTimeout(this.logoutTimeout);
  }

  setTimeout() {
    this.warnTimeout = setTimeout(this.warn, 2940 * 1000);
    this.logoutTimeout = setTimeout(this.logout, 3000 * 1000);
  }

  resetTimeout() {
    this.clearTimeout();
    this.setTimeout();
  }

  warn() {
    alert("Tu sesión se cerrará en 1 minuto.");
  }

  logout() {
    this.props.logout();
    this.destroy();
  }

  destroy() {
    this.clearTimeout();
    for (var i in this.events) {
      window.removeEventListener(this.events[i], this.resetTimeout);
    }
  }

  render() {
    if(typeof this.props.usuario==='undefined')
    return (
      <div className="d-flex justify-content-center centrar-vertical">
        <div className="spinner-border" role="status">
            <span className="sr-only">Loading...</span>
        </div>
      </div>
     )
    if (!this.props.usuario.isAuthenticated) {
    return (
          <Router history={history}>
            <Redirect to="/login"/>
            <Route path="/login" component={Login} ></Route>
            <div className="base"></div>
          </Router>
      )
    }
    for (var i in this.events) {
      window.addEventListener(this.events[i], this.resetTimeout);
    }
    this.setTimeout();
    return (
        <Router history={history}>
          <Header almacen={this.props.usuario.almacen}/>
          <Route path="/usuario" exact component={usuario}></Route>
          <Route path="/almacen" exact component={almacen}></Route>
          <Route path="/MovMat" exact component={movmat}></Route>
          <Route path="/Or" exact component={Or}></Route>
          <Route path="/ubicar" exact component={Ubicar}></Route>
          <Route path="/despacho" exact component={despacho}></Route>
          <Route path="/cuenta" exact component={Cuenta}></Route>
          <Route path="/cuentaitem" exact component={CuentaItem}></Route>
          <Route path="/cuentacuadrar" exact component={CuentaCuadrar} ></Route>
          <StatusBar/>
          <div className="base"></div>
        </Router>
    );
  }
}

const mapStateToProps = state => {
  return {
    usuario: state.api.usuario,
  }
}

export default connect(mapStateToProps, { currentUser, logout } )(App);