import React from 'react';
import {connect} from 'react-redux';
import HeatCycleGraphic from "app/modules/controlPanel/heatCycleGraphic";
import {getLatestProcessdata} from '../../entities/processdata/processdata.reducer';
import {RouteComponentProps} from "react-router";
import {IRootState} from "app/shared/reducers";
import {Row, Col, Container} from 'reactstrap';

export interface IControlPanelProps extends StateProps, DispatchProps, RouteComponentProps<{}> {}

interface IControlPanelState {
  sizefactor: number;
}

export class ControlPanel extends React.Component<IControlPanelProps, IControlPanelState> {
  constructor(props) {
    super(props);

    this.state = {
      sizefactor: 8,
    }

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

  render() {
    return (
      <Container>
        <Row>
          <Col md={9}>
            <div>
              <HeatCycleGraphic processData={this.props.processdataEntity} sizefactor={this.state.sizefactor}/>
            </div>
          </Col>
          <Col md={3}>
xbvgxsdgh
          </Col>
        </Row>
      </Container>
    );
  }
}

const mapStateToProps = ({processdata}: IRootState) => {
  return {
    processdataEntity: processdata.latestEntity
  };
};

const mapDispatchToProps = {
  getLatestProcessdata
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ControlPanel);


