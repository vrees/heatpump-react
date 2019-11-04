import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './processdata.reducer';
import { IProcessdata } from 'app/shared/model/processdata.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProcessdataProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Processdata extends React.Component<IProcessdataProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { processdataList, match } = this.props;
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
                  <th>ID</th>
                  <th>Timestamp</th>
                  <th>High Pressure</th>
                  <th>Low Pressure</th>
                  <th>Evaporating Temperature In</th>
                  <th>Evaporating Temperature Out</th>
                  <th>Pressure Diffence Evaporator</th>
                  <th>Flow Temperature</th>
                  <th>Return Temperature</th>
                  <th>Switch On Sensor Temperature</th>
                  <th>Overheat Temperature</th>
                  <th>Evaporator Out Temperature</th>
                  <th>Heat Request</th>
                  <th>User Confirmation</th>
                  <th>Incident Flow</th>
                  <th>Incident Compressor</th>
                  <th>Incident Low Pressure</th>
                  <th>Incident High Pressure</th>
                  <th>Operating State Water Pump</th>
                  <th>Operating State Compressor</th>
                  <th>Warning Low Pressure</th>
                  <th>Warning High Pressure</th>
                  <th>Alarm Expansion Valve</th>
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
                    <td>{processdata.highPressure}</td>
                    <td>{processdata.lowPressure}</td>
                    <td>{processdata.evaporatingTemperatureIn}</td>
                    <td>{processdata.evaporatingTemperatureOut}</td>
                    <td>{processdata.pressureDiffenceEvaporator}</td>
                    <td>{processdata.flowTemperature}</td>
                    <td>{processdata.returnTemperature}</td>
                    <td>{processdata.switchOnSensorTemperature}</td>
                    <td>{processdata.overheatTemperature}</td>
                    <td>{processdata.evaporatorOutTemperature}</td>
                    <td>{processdata.heatRequest ? 'true' : 'false'}</td>
                    <td>{processdata.userConfirmation ? 'true' : 'false'}</td>
                    <td>{processdata.incidentFlow ? 'true' : 'false'}</td>
                    <td>{processdata.incidentCompressor ? 'true' : 'false'}</td>
                    <td>{processdata.incidentLowPressure ? 'true' : 'false'}</td>
                    <td>{processdata.incidentHighPressure ? 'true' : 'false'}</td>
                    <td>{processdata.operatingStateWaterPump ? 'true' : 'false'}</td>
                    <td>{processdata.operatingStateCompressor ? 'true' : 'false'}</td>
                    <td>{processdata.warningLowPressure ? 'true' : 'false'}</td>
                    <td>{processdata.warningHighPressure ? 'true' : 'false'}</td>
                    <td>{processdata.alarmExpansionValve ? 'true' : 'false'}</td>
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
      </div>
    );
  }
}

const mapStateToProps = ({ processdata }: IRootState) => ({
  processdataList: processdata.entities
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
