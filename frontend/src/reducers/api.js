import _ from 'lodash';
import {
    SIGN_IN,
    CURRENT_USER,
    CURRENT_NAV,
    LOGOUT,
    SIGN_IN_ERROR,
    INIT,
    UBICACIONES,
    SAVE_OREC,
    FETCH_OREC,
    SEND_UBIC,
    FETCH_INVENTARIO,
    SEND_DESPACHO,
} from '../util/types';

export default (state = {}, action) => {
    switch (action.type) {
        case INIT:
            return {...state, materiales: _.mapKeys(action.payload, 'idmaterial')};
        case SIGN_IN:
            return {...state, usuario: { idusuario: action.payload.idUsuario, nombre: action.payload.nombre , isAuthenticated: true, almacen: action.payload.almacen } }
        case SIGN_IN_ERROR:
            return {...state, signin_error: action.payload }
        case LOGOUT:
            return { ...state, usuario: {idusuario: null, nombre: 'anonymousUser', isAuthenticated: false }}
        case CURRENT_USER:
            return { ...state, usuario: action.payload, almacen: action.payload.almacen}
        case CURRENT_NAV:
            return { ...state, currentNav: action.payload }
        case UBICACIONES:
            return { ...state, ubicaciones: action.payload }
        case SAVE_OREC:
            return {...state, respuestaOrec: action.payload }
        case FETCH_OREC:
            return { ...state, tablaOritemPend: _.mapKeys(action.payload, 'idoritem')}
        case SEND_UBIC:
            return { ...state, tablaOritemPend: _.mapKeys(action.payload, 'idoritem')}
        case FETCH_INVENTARIO:
            return { ...state, inventario: action.payload }
        case SEND_DESPACHO:
            return { ...state, respuestaOd: action.payload }
        default:
            return state;
    }
}