import React from 'react';
import { connect } from 'react-redux';
import {currentNav, currentUser, logout } from '../actions';
import { Link } from 'react-router-dom';

class Header extends React.Component {

  componentDidMount() {
    this.props.currentNav(-1);
  }

  logout =() => {
    this.props.logout();
  }

  render() {
    var current = Array(5).fill('nav-link');
    if(this.props.currentPage>=0) {
      current[this.props.currentPage].concat(' active');
    }
    var user = '';
    var almacen = '';
    if(this.props.currentUsuario) {
      user = 'Salir de ' + this.props.currentUsuario.nombre;
      almacen = this.props.almacen;
    }
    return (
      <nav className="navbar navbar-expand-md navbar-dark bg-dark mb-4 nav__component" style={{zIndex: 100}}>
        <span className="navbar-brand">Logisystem</span>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
          <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Administrar</a>
              <div className="dropdown-menu">
                <Link className="dropdown-item" to="/usuario">Usuario</Link>
                <Link className="dropdown-item" to="/almacen">Almacen</Link>
              </div>
            </li>
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Gestión Inventario</a>
              <div className="dropdown-menu">
                <Link className="dropdown-item" to="/MovMat">Movimiento de Material</Link>
                <Link className="dropdown-item" to="/cuenta">Solicitud de Conteo</Link>
                <Link className="dropdown-item" to="/cuentaitem">Conteo de Artículos</Link>
                <Link className="dropdown-item" to="/cuentacuadrar">Cuadrar Conteo</Link>
              </div>
            </li>
            <li className="nav-item">
            </li>
            <li className="nav-item">
              <Link className={current[0]} to="/or">Recepción</Link>
            </li>
            <li className="nav-item">
              <Link className={current[1]} to="/ubicar">Ubicar</Link>
            </li>
            <li className="nav-item">
              <Link className={current[2]} to="/despacho">Despacho</Link>
            </li>
          </ul>
          <ul className="navbar-nav ml-md-auto mt-2 mt-lg-0">
            <li className="nav-item">
            <Link className="nav-link" to="#"><i className="fas fa-warehouse"></i> {almacen}</Link>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/login" onClick={this.logout}>
              {user}
              </a>
            </li>
          </ul>
          </div>
      </nav>
    );
  }
}

const mapStateToProps = state => {
  return {
    currentPage: state.api.currentNav,
    currentUsuario: state.api.usuario
  }
}

export default connect(mapStateToProps, { currentNav, currentUser, logout })(Header);