import React, { Component } from 'react';
import { init, listarMovimiento } from '../actions/index';
import { connect } from 'react-redux';
import {reduxForm, Field} from 'redux-form';
import intlData from '../util/intlData';
import {IntlProvider, FormattedTime, FormattedNumber} from 'react-intl';

class movmat extends Component {

    state = {idmaterial: null }

    componentDidMount() {
        this.props.init();
    }

    renderOption() {
        if(!this.props.materiales){
            return null;
        }
        return this.props.materiales.map(m => {
            return (<option key={m.idmaterial} value={m.idmaterial}>
                        {m.nombre}
                    </option>)
        });
    }

    renderRow() {
        if(!this.props.movimientos){
            return null;
        }
        return this.props.movimientos.map(m => {
            return (<tr key={m.idinventario}>
                        <td>{m.idinventario}</td>
                        <td>{m.nombre}</td>
                        <td><FormattedNumber value={m.cantidad}></FormattedNumber> {m.positivo?<i class="fas fa-arrow-circle-up" style={{color:'blue'}}></i>:<i class="fas fa-arrow-circle-down" style={{color:'red'}}></i>}</td>
                        <td>{m.pos}</td>
                        <td><FormattedTime format="hhmm" value={m.fecharegistro}></FormattedTime></td>
                        <td><b>{m.total}</b></td>
                    </tr>);
        })
    }

    renderTable(){
        return (<table className="table text-center">
                <thead>
                    <tr>
                        <th>id</th>
                        <th>Material</th>
                        <th>Cantidad</th>
                        <th>Ubicación</th>
                        <th>Fecha</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <IntlProvider locale="es" {...intlData}>
                        {this.renderRow()}
                    </IntlProvider>
                </tbody>
        </table>);
    }

    renderMaterial = ({input, label, meta: {touched, error}}) => {
        return (
            <div className="form-row">
                <label htmlFor="idmaterial">{label}</label>
                <select id="idmaterial"
                    className="form-control mb-2"
                    value={this.state.idmaterial}
                    {...input}>
                    <option key="0" value="0">Seleccionar</option>
                    {this.renderOption()}
                </select>
            </div>
        );
    }

    Submit = formValues => {
        this.props.listarMovimiento(formValues.material);
    }

    render() {
        return (<div className="container">
                    <div className="row mb-3">
                        <div className="col-md-5">
                            <div className="card">
                                <div className="card-header">
                                    Buscar
                                </div>
                                <div className="card-body">
                                    <form  onSubmit={this.props.handleSubmit(this.Submit)}>
                                        <Field name="material" label="Material"
                                        component={this.renderMaterial}></Field>
                                        <button type="submit" className="btn btn-primary">
                                            Consultar
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        {this.renderTable()}
                    </div>
                </div>);
    }
}

const mapStateToProps = state => {
    if(!state.api.materiales){
        return {}
    }
    return {
        materiales: Object.values(state.api.materiales),
        movimientos: state.api.movimientos,
    }
}

const form = reduxForm({
    form:'movmat'
})(movmat);

export default connect(mapStateToProps, { init, listarMovimiento })(form);