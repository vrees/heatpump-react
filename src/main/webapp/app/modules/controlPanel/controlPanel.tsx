import React from 'react';
import {connect} from 'react-redux';
import HeatCycleGraphic from "app/modules/controlPanel/heatCycleGraphic";
import {getLatestProcessdata} from '../../entities/processdata/processdata.reducer';
import {RouteComponentProps} from "react-router";
import {IRootState} from "app/shared/reducers";

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

/*  static getDerivedStateFromProps(props, state) {
    /!* eslint-disable no-console *!/
    console.log("ControlPanel getDerivedStateFromProps called: ", props.sizefactor);
    /!* eslint-enable no-console *!/

    if (props.sizefactor !== state.sizefactor) {
      return {
        sizefactor: props.sizefactor
      };
    }
    return null;
  }*/


  componentDidMount() {
    /* eslint-disable no-console */
    console.log("ControlPanel componentDidMount called");
    /* eslint-enable no-console */
    this.props.getLatestProcessdata();
    this.update
  }

  update = () => {
    const minSizefactor = 3;
    const sizefactorW = (window.innerWidth - 100) / 110;
    const sizefactorH = (window.innerHeight - 182) / 70;
    const factor = Number((sizefactorW < sizefactorH ? sizefactorW : sizefactorH).toFixed(1));
    const newSizeFactor = factor < minSizefactor ? minSizefactor : factor;

    this.setState({
      sizefactor: newSizeFactor
    });
//    const updateSizefactor1 = this.props.updateSizefactor(sizefactor);
  };

  render() {
    return (
      <div>
        <HeatCycleGraphic processData={this.props.processdataEntity} sizefactor={this.state.sizefactor}/>
      </div>
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


