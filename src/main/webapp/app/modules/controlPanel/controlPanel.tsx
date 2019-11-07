import React, {Component} from 'react';
import {connect} from 'react-redux';
import {findDOMNode} from 'react-dom';
import {Home} from "app/modules/home/home";
import HeatCycleGraphic from "app/modules/controlPanel/heatCycleGraphic";
import {IProcessdata} from "app/shared/model/processdata.model";
import moment from "moment";
import {IPaginationBaseState} from "react-jhipster";
import {getEntity, getLatestProcessdata} from '../../entities/processdata/processdata.reducer';
import {RouteComponentProps} from "react-router";
import {IRootState} from "app/shared/reducers";
import {ProcessdataDetail} from "app/entities/processdata/processdata-detail";


export type IControlPanelProps = DispatchProps;

export type IProcessdataState = IPaginationBaseState;

interface IControlPanelState {
  sizefactor: number;
  processdata: IProcessdata;
}

export class ControlPanel extends Component<IControlPanelProps, IControlPanelState> {

  constructor(props) {
    super(props);

    this.state = {
      sizefactor: 8,
      processdata: null,
    }

    window.addEventListener("resize", this.update);
  }

  componentDidMount() {
    this.update();
  }


  update = () => {
    /* eslint-disable no-console */
    console.log("tpeof  this.props.getLatestProcessdata", typeof this.props.getLatestProcessdata);
    /* eslint-enable no-console */
    const latestProcessdata = this.props.getLatestProcessdata();
    const minSizefactor = 3;
    const sizefactorW = (window.innerWidth - 100) / 110;
    const sizefactorH = (window.innerHeight - 182) / 70;
    const factor = Number((sizefactorW < sizefactorH ? sizefactorW : sizefactorH).toFixed(1));

    this.setState({
      sizefactor: factor < minSizefactor ? minSizefactor : factor
    });
  };

  render() {
    return (
      <div>
        <HeatCycleGraphic processData={this.state.processdata} sizefactor={this.state.sizefactor}/>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {};
}

const mapDispatchToProps = {getLatestProcessdata};
type DispatchProps = typeof mapDispatchToProps;


export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ControlPanel);
