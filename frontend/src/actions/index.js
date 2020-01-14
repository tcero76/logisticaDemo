import {
    SIGN_IN,
    ACCESS_TOKEN,
    CURRENT_USER,
    CURRENT_NAV,
    SIGN_IN_ERROR,
    LOGOUT,
    INIT,
    UBICACIONES,
    SAVE_OREC,
    FETCH_OREC,
    SEND_UBIC,
} from '../util/types';
import history from '../util/history';
import api from '../util/api';

export const currentNav = currentnav  => {
    return {type: CURRENT_NAV, payload: currentnav};
}

export const init = () => async dispatch => {
    const response = await api().get('/material/materiales');
    dispatch({ type: INIT, payload: {...response.data }});
}

export const signin = sign_in => async dispatch => {
    await api().post('/signin', sign_in)
    .then( async response => {
        await localStorage.setItem(ACCESS_TOKEN, response.data.accessToken);
        await dispatch({ type: SIGN_IN, payload: { ...response.data}});
        history.push('/');
    })
    .catch( e => {
        dispatch({ type: SIGN_IN_ERROR, payload: e.response.data.message});
    });
}
export const currentUser = () => async dispatch => {
    const response = await api().get('/user/usuario');
    dispatch({ type: CURRENT_USER, payload: { ...response.data}});
}
export const logout = () => async dispatch => {
    await localStorage.removeItem(ACCESS_TOKEN);
    const usuario = await api().get('/user/usuario');
    dispatch({ type: LOGOUT, payload: { ...usuario.data}});
    history.push('/login');
}

export const listarAlmacen = () => async dispatch => {
    const response = await api().get('/ubicar/ubicaciones');
    dispatch({ type: UBICACIONES, payload: response.data })
}

export const save_orec = state => async dispatch => {
    var orecRequest = { guiadespacho: state.guiadespacho,
                        oritems: state.oritems}
    const response = await api().post('/material/save',orecRequest);
    var orec = null;
    if(response.status===200) {
        orec = response.data;
    }
    dispatch({ type: SAVE_OREC, payload: orec });
    history.push('/or');
}

export const listarOrec = () => async dispatch => {
    var response = await api().get('/ubicar/listado');
    dispatch({type: FETCH_OREC, payload: response.data})
}

export const enviarUbicacion = (oritem) => async dispatch => {
    var response = await api().patch('/ubicar/ubicacion', oritem);
    dispatch({type: SEND_UBIC, payload: response.data })
}
