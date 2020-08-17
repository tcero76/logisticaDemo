import _ from 'lodash';
import {
    SIGN_IN,
    CURRENT_USER,
    CURRENT_NAV,
    LOGOUT,
    ERROR_RESPONSE,
} from '../util/types';
export default (state = {}, action) => {
    switch (action.type) {
        case ERROR_RESPONSE:
            return { ...state, msg: action.payload.data.message, status: action.payload.status }
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