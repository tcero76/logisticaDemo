import React, { Component } from 'react';
import intlData from '../../util/intlData';
import {IntlProvider} from 'react-intl';

class Tabla extends Component {

    previousPage = (e) => {
        e.preventDefault();
        if (this.page > 1) {
            this.page--;
        }
        this.props.fetchData(this.page);
    }

    nextPage = (e) => {
        e.preventDefault();
        if (this.page < this.props.totalPage) {
            this.page++;
        }
        this.props.fetchData(this.page);
    }

    renderPaginacionItems() {
        var arrPage = Array(this.props.totalPage).fill(0).map((p,i) => i+1);
        return arrPage.map(p => {
            const classname = p==this.props.page?"page-item active":"page-item";
            return (
                <li className={classname}>
                    <a className="page-link" href="#" onClick={e => this.SubmitPage(e)}>
                        {p}
                    </a>
                </li>
            )
        })
    }

    renderPaginacion() {
        return (<nav aria-label="Page navigation example">
                    <ul className="pagination">
                        <li className="page-item">
                        <a className="page-link" href="#" onClick={e => this.previousPage(e)} aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                        </li>
                        {this.renderPaginacionItems()}
                        <li className="page-item">
                        <a className="page-link" href="#" onClick={e => this.nextPage(e)}
                            aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                        </li>
                    </ul>
                </nav>
        )
    }

    SubmitPage(e) {
        e.preventDefault();
        this.page = e.target.text;
        this.props.fetchData(this.page);
    }

    render(){
        if(!this.props.rows) {
            return null;
        }
        return (<div className="row">
                    {this.renderPaginacion()}
                    <table className="table text-center">
                            <thead>
                                {this.props.renderHeaders}
                            </thead>
                            <tbody>
                                <IntlProvider locale="es" {...intlData}>
                                    {this.props.renderRows()}
                                </IntlProvider>
                            </tbody>
                    </table>
                </div>);
    }
}

export default Tabla;