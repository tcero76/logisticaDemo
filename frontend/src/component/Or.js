import React from 'react';
import { init, currentNav, save_orec } from '../actions';
import { connect } from 'react-redux';
import '../scss/style.scss';
import { Field, reduxForm } from 'redux-form';
import { IntlProvider, FormattedNumber } from 'react-intl';
import intlData from '../util/intlData';
import NumberFormat from 'react-number-format';

class Or extends React.Component {

    state = { guiadedespacho: null, oritems: [], alert:false }

    componentDidMount() {
        this.props.init();
        this.props.currentNav(0);
        this.setTimeOut();
    }

    setTimeOut() {
        this.alertTime = setTimeout(() => {this.setState({...this.state, alert:false})},5000);
    }

    clearTimeOut() {
        clearTimeout(this.alertTime);
    }

    renderTableRow() {
        return this.state.oritems.map(fila => {
            return (
                <tr key={fila.material.idmaterial}>
                    <td>{fila.material.nombre}</td>
                    <td>{fila.pos}</td>
                    <td>
                        <FormattedNumber value={fila.cantidad} />
                    </td>
                </tr>
            );
        })
    }

    onSubmitOrec = () => {
        this.props.save_orec(this.state);
        this.props.reset();
        this.setState({ guiadedespacho: null, oritems: [], alert: true});
        this.clearTimeOut()
        this.setTimeOut();
    }

    renderTable() {
        if(this.state.oritems.length<1)
            return null;
        return (
            <div className="table-responsive">
                <table className="table">
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>pos</th>
                            <th>Cantidad</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderTableRow()}
                    </tbody>
                </table>
                <button className="btn btn-primary" onClick={this.onSubmitOrec}>Guardar</button>
            </div>
        )
    }

    onSubmit = formValues => {
        var mat = this.props.materiales
                    .find(m => m.idmaterial===parseInt(formValues.idmaterial));

        var pos = this.state.oritems.length+1; 
        this.state.oritems.push({
            pos ,
            cantidad: parseFloat(formValues.cantidad),
            material: {idmaterial: parseInt(formValues.idmaterial),
                       nombre: mat.nombre}
            });
        this.setState({...this.state,
            guiadespacho: formValues.guiadespacho});
        var guia = formValues.guiadespacho;
        this.props.reset();
        this.props.change('guiadespacho',guia);
    }

    renderMaterial = ({ input, label, meta: {touched, error} }) => {
        var classNam='form-control';
        if(touched) {
            classNam = `form-control ${error ? ' is-invalid' : ' is-valid'}`;
        }
        return (
            <div className="col-md-6 mb-3">
                    <label htmlFor="material">{label}</label>
                    <select id="material" className={classNam} {...input}>
                        <option key={null} value={0}>Seleccionar</option>
                        {this.props.materiales.map(mat => <option key={mat.idmaterial} value={mat.idmaterial}>{mat.nombre}</option>)}
                    </select>
                    <div className="invalid-feedback">
                        {error}
                    </div>
                    <div className="valid-feedback">
                        Ok.
                    </div>
            </div>
        );
    }

    renderCantidad = ({input, label, meta: {touched, error}}) => {
        var classNam='form-control';
        if(touched) {
            classNam = `form-control ${error ? ' is-invalid' : ' is-valid'}`;
        }
        return (
            <div className="col-md-3 mb-3">
                <label htmlFor="cantidad">{label}</label>
                <NumberFormat id="cantidad" className={classNam} {...input} autoComplete="off"
                    thousandSeparator="." decimalSeparator="," ></NumberFormat>
                    <div className="invalid-feedback">
                        {error}
                    </div>
                    <div className="valid-feedback">
                        Ok.
                    </div>
            </div>
        );
    }

    renderFormRow() {
        return (
            <div className="form-row mb-2 form__fila">
                <Field name="idmaterial" component={this.renderMaterial} label="Nombre del Material"></Field>
                <Field name="cantidad" component={this.renderCantidad} label="Cantidad" autocomplete="off"></Field>
                <div className="col-md-2 mb-3" style={{top:'2rem'}}>
                    <button className="btn btn-primary">Agregar</button>
                </div>
                
            </div>
        )
    }

    renderGuia({input, label, meta: {touched, error}}) {
        var classNam = 'form-control';
        if(touched) {
            classNam = `form-control ${error?' is-invalid':'is-valid'}`;
        }
        return (
            <div className="col-md-4">
                <label htmlFor="guiadespacho">{label}</label>
                <input id="guiadespacho" name="guiadespacho" {...input} className={classNam} autoComplete="off"></input>
                <div className="invalid-feedback">{error}</div>
                <div className="valid-feedback">Ok</div>
            </div>
        )
    }

    renderFormGuia() {
        return (
            <div className="form-row form__fila">
                <Field name="guiadespacho" component={this.renderGuia}
                label="Guia de Despacho"></Field>
            </div>
        )
    }

    renderAlert() {
        if(!this.state.alert) {
            return null;
        }
        if(this.props.respuestaOrec===null) {
            return null;
        }
        return (
            <div className="alert alert-success" role="alert">
                idOrec {this.props.respuestaOrec} guardada
            </div>
        )
    }

    renderForm() {
        return (
            <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                {this.renderAlert()}
                {this.renderFormGuia()}
                {this.renderFormRow()}
            </form>
        );
    }

    render() {
        if (typeof this.props.materiales === 'undefined') {
            return (
                <div className="d-flex justify-content-center centrar-vertical">
                  <div className="spinner-border" role="status">
                      <span className="sr-only">Loading...</span>
                  </div>
                </div>
            );
        }
        return (
            <IntlProvider locale="es" {...intlData}>
                <div className="container">
                    <h1 className="mb-3">Recepción Ors</h1>
                    <div className="card mb-4 card__component">
                        <div className="card-body">
                            {this.renderForm()}
                        </div>
                    </div>
                    <div className="table__container">
                        {this.renderTable()}
                    </div>
                </div>
            </IntlProvider>
        );
    }
}

const mapStateToProps = state => {
    var materiales = state.api.materiales;
    if (materiales) {
        materiales = Object.values(materiales);
    }
    var respuestaOrec = state.api.respuestaOrec;
    if(!respuestaOrec) {
        respuestaOrec = null;
    }
    return {
        materiales,
        respuestaOrec,
    }
}

const validate = (formValues, state) => {
    const error = {};
    if (formValues.idmaterial==='0') {
        error.idmaterial = "Debe seleccionar un material.";
    }
    if(!formValues.cantidad) {
        error.cantidad = "Debe ingresar cantidad.";
    }
    if(!formValues.guiadespacho) {
        error.guiadespacho = "Ingresar Guía de Despacho.";
    }
    return error;
}

const form = reduxForm({
    form: 'orForm',
    validate
})(Or);

export default connect(mapStateToProps, { init, currentNav, save_orec })(form);