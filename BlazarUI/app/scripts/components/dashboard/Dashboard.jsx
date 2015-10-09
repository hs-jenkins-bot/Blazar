/*global config*/
import React, {Component, PropTypes} from 'react';
import SectionLoader from '../shared/SectionLoader.jsx';
import StarredModules from './StarredModules.jsx';
import Headline from '../shared/headline/Headline.jsx';
import PageHeader from '../shared/PageHeader.jsx';
import UIGrid from '../shared/grid/UIGrid.jsx';
import UIGridItem from '../shared/grid/UIGridItem.jsx';
import MutedMessage from '../shared/MutedMessage.jsx';
import Icon from '../shared/Icon.jsx';
import EmptyMessage from '../shared/EmptyMessage.jsx';
import Breadcrumb from '../shared/Breadcrumb.jsx';
import Helpers from '../ComponentHelpers';
import Hosts from './Hosts.jsx';

class Dashboard extends Component {

  render() {
    if (this.props.loading) {
      return (
        <SectionLoader />
      );
    }

    return (
      <div>
        <PageHeader>
          <Breadcrumb
            appRoot={config.appRoot}
            params={this.props.params}
          />
        </PageHeader>

        <UIGrid>
          <UIGridItem size={12} classname='dashboard-unit'>
            <Headline>
              Organizations
            </Headline>
            <Hosts 
              hosts={this.props.hosts}
              loadingHosts={this.props.loadingHosts}
            />
          </UIGridItem>
                
          <UIGridItem size={12} classname='dashboard-unit'>
            <Headline>
              Starred Modules
            </Headline>
            <StarredModules 
              modulesBuildHistory={this.props.modulesBuildHistory}
              loading={this.props.loading}
            />
          </UIGridItem>
        </UIGrid>

      </div>
    );
  }
}

Dashboard.propTypes = {
  loading: PropTypes.bool,
  loadingHosts: PropTypes.bool,
  modulesBuildHistory: PropTypes.array,
  hosts: PropTypes.array,
  params: PropTypes.object
};

export default Dashboard;
