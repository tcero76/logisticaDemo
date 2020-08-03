import {
    SIGN_IN,
    ACCESS_TOKEN,
    CURRENT_USER,
    CURRENT_NAV,
    LOGOUT,
    POST,
    FETCH_UBICACIONES,
    FETCH_DATA,
    FETCH_DATA_LIST,
    FETCH_PAGINABLE,
    FETCH_ZONA,
    FETCH_ZONA_DETAIL,
    SEND_CUENTAITEM,
    ERROR_RESPONSE,
    RESET,
} from '../util/types';
import history from '../util/history';
import api from '../util/api';

export const currentNav = currentnav  => {
    return {type: CURRENT_NAV, payload: currentnav};
}

export const signin = sign_in => dispatch => {
    api().post('/signin', sign_in)
        .then(response => {
            localStorage.setItem(ACCESS_TOKEN, response.data.accessToken);
            dispatch({ type: SIGN_IN, payload: { ...response.data } });
        })
        .catch(e => {
            dispatch({ type: ERROR_RESPONSE, payload: e.response });
        });
}
export const currentUser = () => dispatch => {
    api().get('/usuarios/current')
    .then(res => {
        dispatch({ type: CURRENT_USER, payload: { ...res.data } });
    }).catch(e => {
        dispatch({type: ERROR_RESPONSE, payload: e.response})
    })
}

export const logout = () => async dispatch => {
    await localStorage.removeItem(ACCESS_TOKEN);
    const usuario = await api().get('/usuarios/current');
    dispatch({ type: LOGOUT, payload: { ...usuario.data } });
    history.push('/login');
}

