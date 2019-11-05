import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './processdata.reducer';
import { IProcessdata } from 'app/shared/model/processdata.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IProcessdataProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IProcessdataState = IPaginationBaseState;

export class Processdata extends React.Component<IProcessdataProps, IProcessdataState> {
  state: IProcessdataState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { processdataList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="processdata-heading">
          Processdata
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Processdata
          </Link>
        </h2>
        <div className="table-responsive">
          {processdataList && processdataList.length > 0 ? (
            <Table responsive aria-describedby="processdata-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('timestamp')}>
                    Timestamp <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('temperatureEvaporatingIn')}>
                    Temperature Evaporating In <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('temperatureEvaporatingOut')}>
                    Temperature Evaporating Out <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('temperatureFlow')}>
                    Temperature Flow <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('temperatureReturn')}>
                    Temperature Return <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('temperatureSwitchOnSensor')}>
                    Temperature Switch On Sensor <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('temperatureOverheatedGas')}>
                    Temperature Overheated Gas <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('pressureHigh')}>
                    Pressure High <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('pressureLow')}>
                    Pressure Low <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('pressureDiffenceEvaporator')}>
                    Pressure Diffence Evaporator <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('heatRequest')}>
                    Heat Request <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('userConfirmation')}>
                    User Confirmation <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('alarmExpansionValve')}>
                    Alarm Expansion Valve <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('incidentFlow')}>
                    Incident Flow <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('incidentCompressor')}>
                    Incident Compressor <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('incidentLowPressure')}>
                    Incident Low Pressure <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('incidentHighPressure')}>
                    Incident High Pressure <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('operatingStateWaterPump')}>
                    Operating State Water Pump <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('operatingStateCompressor')}>
                    Operating State Compressor <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('calculatedOverheatTemperature')}>
                    Calculated Overheat Temperature <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('warningLowPressure')}>
                    Warning Low Pressure <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('warningHighPressure')}>
                    Warning High Pressure <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {processdataList.map((processdata, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${processdata.id}`} color="link" size="sm">
                        {processdata.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={processdata.timestamp} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{processdata.temperatureEvaporatingIn}</td>
                    <td>{processdata.temperatureEvaporatingOut}</td>
                    <td>{processdata.temperatureFlow}</td>
                    <td>{processdata.temperatureReturn}</td>
                    <td>{processdata.temperatureSwitchOnSensor}</td>
                    <td>{processdata.temperatureOverheatedGas}</td>
                    <td>{processdata.pressureHigh}</td>
                    <td>{processdata.pressureLow}</td>
                    <td>{processdata.pressureDiffenceEvaporator}</td>
                    <td>{processdata.heatRequest ? 'true' : 'false'}</td>
                    <td>{processdata.userConfirmation ? 'true' : 'false'}</td>
                    <td>{processdata.alarmExpansionValve ? 'true' : 'false'}</td>
                    <td>{processdata.incidentFlow ? 'true' : 'false'}</td>
                    <td>{processdata.incidentCompressor ? 'true' : 'false'}</td>
                    <td>{processdata.incidentLowPressure ? 'true' : 'false'}</td>
                    <td>{processdata.incidentHighPressure ? 'true' : 'false'}</td>
                    <td>{processdata.operatingStateWaterPump ? 'true' : 'false'}</td>
                    <td>{processdata.operatingStateCompressor ? 'true' : 'false'}</td>
                    <td>{processdata.calculatedOverheatTemperature}</td>
                    <td>{processdata.warningLowPressure ? 'true' : 'false'}</td>
                    <td>{processdata.warningHighPressure ? 'true' : 'false'}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${processdata.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${processdata.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${processdata.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Processdata found</div>
          )}
        </div>
        <div className={processdataList && processdataList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ processdata }: IRootState) => ({
  processdataList: processdata.entities,
  totalItems: processdata.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Processdata);
