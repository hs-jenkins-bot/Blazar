import React, {Component, PropTypes} from 'react';
import BuildStates from '../../constants/BuildStates.js';
import { Link } from 'react-router';
import {LABELS, iconStatus} from '../constants';

import {tableRowBuildState, timestampFormatted, humanizeText, buildResultIcon} from '../Helpers';

import Icon from '../shared/Icon.jsx';
import Sha from '../shared/Sha.jsx';


class BranchBuildHistoryTableRow extends Component {

  
  renderSha() {
    const {data, params} = this.props;
    let sha;

    if (data.sha !== undefined) {
      const gitInfo = {
        host: params.host,
        organization: params.org,
        repository: params.repo,
      }

      return (
        <Sha gitInfo={gitInfo} build={data} />  
      );
    }  
  }
  
  renderDuration() {
    const {data} = this.props;

    let duration = data.duration;
    if (data.state === BuildStates.IN_PROGRESS) {
      duration = 'In Progress...';
    }

    return duration;
  }
  
  renderStartTime() {
    return timestampFormatted(this.props.data.startTimestamp);
  }

  render() {
    const {data, params} = this.props;
    let stateToRender = '';

    if (data.state === BuildStates.IN_PROGRESS) {
      stateToRender = params.prevBuildState;
    }

    return (
      <tr className={tableRowBuildState(data.state)}>
        <td className='build-status'>
          {buildResultIcon(data.state, stateToRender)}
        </td>
        <td className='build-result-link'>
          <Link to={data.blazarPath}>{data.buildNumber}</Link>
        </td>
        <td>
          {this.renderStartTime()}
        </td>
        <td>
          {this.renderDuration()}
        </td>
        <td>
          {this.renderSha()}
        </td>
      </tr>
    );
  }
}

BranchBuildHistoryTableRow.propTypes = {
  data: PropTypes.object.isRequired
};

export default BranchBuildHistoryTableRow;
