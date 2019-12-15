import React from 'react';
import {connect} from 'react-redux';
import HeatCycleGraphic from "app/modules/controlPanel/heatCycleGraphic";
import {getLatestProcessdata, sendEvent} from '../../entities/processdata/processdata.reducer';
import {RouteComponentProps} from "react-router";
import {IRootState} from "app/shared/reducers";
import {Row, Col, Input, CustomInput, Alert, Card, CardBody, Collapse, Badge, Button} from 'reactstrap';
import {websocketConnect, websocketDisconnect, websocketIsConnected} from "app/config/websocket-middleware";
import {StateInfo, States} from "app/shared/model/enumerations/states.model";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {ActionButton} from "app/shared/model/enumerations/actionButton.model";

export interface IControlPanelProps extends StateProps, DispatchProps, RouteComponentProps<{}> {
}

interface IControlPanelState {
  sizefactor: number;
}

export class ControlPanel extends React.Component<IControlPanelProps, IControlPanelState> {
  constructor(props) {
    super(props);

    this.state = {
      sizefactor: 8,
    }

    // this.handleRefresh = this.handleRefresh.bind(this)

    window.addEventListener("resize", this.update);
  }

  componentDidMount() {
    /* eslint-disable no-console */
    console.log("ControlPanel componentDidMount called");
    this.props.getLatestProcessdata();
    this.update();
    console.log("ControlPanel componentDidMount called - after calling update: ", this.state.sizefactor);
    /* eslint-enable no-console */
  }

  update = () => {
    const minSizefactor = 3;
    const sizefactorW = (window.innerWidth - 100) / 110;
    const sizefactorH = (window.innerHeight - 182) / 70;
    const factor = Number((sizefactorW < sizefactorH ? sizefactorW : sizefactorH).toFixed(1));
    const newSizeFactor = factor < minSizefactor ? minSizefactor : factor;

    /* eslint-disable no-console */
    console.log("newSizeFactor: ", newSizeFactor);
    /* eslint-enable no-console */

    this.setState({
      sizefactor: newSizeFactor
    });
  };

  handleConnection = (event) => {
    if (websocketIsConnected())
      websocketDisconnect();
    else
      websocketConnect();
  };

  sendStateEvent = (eventName: string) => (event: any) => {
    this.props.sendEvent(eventName)

    if (!websocketIsConnected()) {
      this.handleRefresh();
    }
    return;
  }

  handleRefresh = () => {
    /* eslint-disable no-console */
    console.log("refreshing ...");
    /* eslint-enable no-console */

    this.props.getLatestProcessdata();
  }

  render() {
    const processData = this.props.processdataEntity;
    let {state = States.UNDEFINED} = processData;
    state = state == null ? States.UNDEFINED : state;

    const {color, buttons, label} = StateInfo.get(state);

    return (
      <Row>
        <Col lg={9}>
          <div>
            <HeatCycleGraphic processData={processData} sizefactor={this.state.sizefactor}/>
          </div>
        </Col>
        <Col lg={3}>
          <Row className="mt-3">
            <Col lg={6}>
              <h1><Badge color={color}>{label}</Badge></h1>
            </Col>
          </Row>
          <Row className="mt-3">
            <Col lg={6}>
              <CustomInput type="switch" id="websocketConnedt" name="websocketConnet"
                           label="Auto-Update"
                           onClick={this.handleConnection}/>
            </Col>
            <Col lg={6}>
              <button onClick={this.handleRefresh}>refresh</button>
            </Col>
          </Row>
          <Row className="mt-3">
            <Col lg={12}>
              <Button id="switch-on" color="primary" onClick={this.sendStateEvent("SWITCH_ON")}
                      disabled={!buttons.includes(ActionButton.SWITCH_ON)}>einschalten</Button>
              &nbsp;
              <Button id="switch-off" color="dark" onClick={this.sendStateEvent("SWITCH_OFF")}
                      disabled={!buttons.includes(ActionButton.SWITCH_OFF)}>ausschalten</Button>
              &nbsp;
              <Button id="ackknowledge" color="success" onClick={this.sendStateEvent("ACKNOWLEDGE")}
                      disabled={!buttons.includes(ActionButton.ACKNOWLEDGE)}>quittieren</Button>
            </Col>
          </Row>
          <Row className="mt-3">
            <Collapse isOpen={processData.messages !== undefined && processData.messages.length > 0}>
              <Card>
                <CardBody>
                  {processData.messages !== undefined &&
                  processData.messages.map((message, i) => (
                    <Alert color="danger" key={i}>
                      {message.msg}
                    </Alert>
                  ))}
                </CardBody>
              </Card>
            </Collapse>
          </Row>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({processdata}: IRootState) => {
  return {
    processdataEntity: processdata.latestEntity
  };
};

const mapDispatchToProps = {
  getLatestProcessdata, sendEvent
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ControlPanel);


