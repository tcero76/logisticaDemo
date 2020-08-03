import _ from 'lodash';
import {
    SIGN_IN,
    CURRENT_USER,
    CURRENT_NAV,
    LOGOUT,
    POST,
    FETCH_DATA,
    FETCH_DATA_LIST,
    FETCH_PAGINABLE,
    FETCH_ZONA,
    FETCH_ZONA_DETAIL,
    SEND_CUENTAITEM,
    ERROR_RESPONSE,
    RESET,
    FETCH_UBICACIONES,
} from '../util/types';

export default (state = {}, action) => {
    switch (action.type) {
        case FETCH_UBICACIONES:
            return { ...state, ubicaciones: action.payload }
        case FETCH_DATA:
            return { ...state, rows: action.payload }
        case FETCH_DATA_LIST:
            return { ...state, items:action.payload }
        case FETCH_ZONA:
            return { ...state, zonas: action.payload }
        case FETCH_ZONA_DETAIL:
            return { ...state, zonaDetalle: action.payload }
        case FETCH_PAGINABLE:
            return { ...state, rows: action.payload.list,
                        totalPage:action.payload.totalPag,
                        page: action.payload.page }
        case POST:
            return {...state, respuestaOrec: action.payload ,
                status: action.payload.status, msg: action.payload.data}
        case ERROR_RESPONSE:
            return { ...state, 
                msg: action.payload.data.message, status: action.payload.status }
        case RESET:
            return { ...state, items: {}, datos: [], msg: null, status: null, 
            rows: null, page: null, totalPage: null}
        case SIGN_IN:
            return {...state, usuario: { idusuario: action.payload.idUsuario,
                nombre: action.payload.nombre ,
                isAuthenticated: true,
                almacen: action.payload.almacen } }
        case LOGOUT:
            return { ...state, usuario: {idusuario: null,
                nombre: 'anonymousUser', isAuthenticated: false } }
        case CURRENT_USER:
            return { ...state, usuario: action.payload, almacen: action.payload.almacen }
        case CURRENT_NAV:
            return { ...state, currentNav: action.payload}
        default:
            return state;
    }
}