import React, { Component } from 'react';
import { connect } from 'react-redux';
import { init } from '../../actions';
import { IntlProvider, FormattedNumber } from 'react-intl';
import IntlData from '../../util/intlData';
import lunr from 'lunr';

class SelMaterial extends Component {


    componentDidMount() {
        this.props.init();
        this.seleccionadoText = document.getElementById("seleccion");
    }

    componentDidUpdate() {
        if(this.props.materiales){
            this.idx = lunr((e) => {
                e.ref('idmaterial')
                e.field('nombre')
                e.field('precio');
                this.props.materiales.forEach(m => e.add(m));
              });
        }
    }

    state = {materiales: [], lista:[], cursor: 0, seleccionado:null}

    renderInput() {
    return (
        <div className="input-group mb-1 selMaterial__input">
            <div className="input-group-prepend">
                <span className="input-group-text">
                    <svg className="bi bi-search" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fillRule="evenodd" d="M10.442 10.442a1 1 0 011.415 0l3.85 3.85a1 1 0 01-1.414 1.415l-3.85-3.85a1 1 0 010-1.415z" clipRule="evenodd"/>
                        <path fillRule="evenodd" d="M6.5 12a5.5 5.5 0 100-11 5.5 5.5 0 000 11zM13 6.5a6.5 6.5 0 11-13 0 6.5 6.5 0 0113 0z" clipRule="evenodd"/>
                    </svg>
                </span>
            </div>
            <div contentEditable="true"
                id="seleccion"
                style={{width: '20rem'}}
                onFocus={e => this.onFocusMaterial()}
                onBlur={e => this.onBlurMaterial()}
                onChange={e => this.onChangeSearch(e)}
                onKeyDown={e => this.onKeyDownMaterial(e)}
                className="form-control">
            </div>
        </div>
    );
    }

    renderList() {
        if(!this.state.lista) {
            return null;
        }
        return this.state.lista
            .map((m,i) => {
                var material = this.props.materiales
                                .find(mat => mat.idmaterial === parseInt(m.ref));
                if(!material){
                    return null;
                }
                var clase = 'list-group-item list-group-item-action';
                return (
                    <div key={m.ref}
                        onMouseDown={e => this.onClickItem(m.ref)}
                        className="list-group selMaterial__lista__item">
                            <a href="#"
                                className={i===this.state.cursor?clase.concat(' active'):clase}>
                                <div className="d-flex w-100 justify-content-between">
                                <h5 className="mb-1">{material.nombre}</h5>
                                <small>
                                    <FormattedNumber
                                    value={parseInt(material.precio)}
                                    format="CLP"
                                    >
                                    </FormattedNumber>
                                </small>
                                </div>
                                <p className="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
                            </a>
                        </div>
                );
            })
    }

    onKeyDownMaterial(e) {
        if(e.key==='Enter'){
            e.preventDefault();
            var seleccion = this.state.lista[this.state.cursor].ref;
            this.setState({seleccionado: seleccion});
            this.insertBadge(seleccion);
        }
        this.setState({lista:this.idx.search(e.target.innerText+'*')});
        if(this.state.lista.length-1>this.state.cursor && e.key==='ArrowDown'){
            this.setState({cursor: this.state.cursor+1});
        }
        if(this.state.cursor>0 && e.key==='ArrowUp'){
            this.setState({cursor: this.state.cursor-1});
        }
    }

    onClickItem(key) {
        this.setState({seleccionado: key});
        this.insertBadge(key);
    }

    insertBadge(key) {
        while(this.seleccionadoText.firstChild) {
            this.seleccionadoText.removeChild(this.seleccionadoText.lastChild)
        }
        var badge = document.createElement("span")
        badge.setAttribute("contenteditable", false);
        badge.classList.add("badge", "badge-info");
        badge.innerText = this.props.materiales
                            .find(l => l.idmaterial == key)
                            .nombre;
        this.seleccionadoText.appendChild(badge);
        this.setState({lista: []});
    }

    onFocusMaterial(e) {
        while (this.seleccionadoText.firstChild) {
            this.seleccionadoText.removeChild(this.seleccionadoText.lastChild);
        }
        this.setState({materiales: this.props.materiales});
    }

    onBlurMaterial() {
        this.setState({ lista: []});
    }

    render() {
        return (
            <IntlProvider locale="es" {...IntlData}>
            <div className="container">
                <div className="row">
                    <div className="col-mb-3 selMaterial">
                        {this.renderInput()}
                        <div className="selMaterial__lista">
                            {this.renderList()}
                        </div>
                    </div>
                </div>
            </div>
            </IntlProvider>
            );
    }
}

const mapStateToProps = (state) => {
    if(!state.api.materiales){
        return {};
    }
    return {materiales: Object.values(state.api.materiales)}
}

export default connect( mapStateToProps,{ init })(SelMaterial);