import React from 'react';
import config from '../../../config';
let Link = require('react-router').Link;
import { bindAll } from 'underscore';

class Module extends React.Component {
  render() {
    let name = this.props.name;
    let moduleLink = `${config.appRoot}/${this.props.link}`;
    return <Link to={moduleLink} className='sidebar__repo-module'>{name}</Link>;
  }
}

Module.propTypes = {
  name: React.PropTypes.string,
  link: React.PropTypes.string
};


class ProjectSidebarListItem extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      expanded: false
    };

    bindAll(this, ['handleModuleExpand']);
  }

  handleModuleExpand() {
    this.setState( { expanded: !this.state.expanded } );
  }

  getModulesClassNames() {
    let classNames = 'sidebar__modules';
    if (this.state.expanded) {
      classNames += ' expanded';
    }
    return classNames;
  }

  render() {

    let modules = [];
    let repoDetail = this.props.repo;
    let moduleGitInfo = repoDetail[0].gitInfo;
    let repoLink = `${moduleGitInfo.host}/${moduleGitInfo.organization}/${moduleGitInfo.repository}`;

    repoDetail.forEach( (repo) => {

      let {
        gitInfo,
        module,
        buildState
      } = repo;

      let moduleLink = `${gitInfo.host}/${gitInfo.organization}/${gitInfo.repository}/${gitInfo.branch}/${module.name}/${buildState.buildNumber}`;

      modules.push(
        <Module key={buildState.buildNumber} name={module.name} link={moduleLink} />
      );

    });

    return (
      <div className='sidebar__repo-container'>
        <div className='sidebar__repo-url'>
          {repoLink}
        </div>
        <div className='sidebar__repo' onClick={this.handleModuleExpand}>
          <div className="la-ball-scale la-sm sidebar__active-building-icon"><div></div></div>
          {repoDetail.repository}
        </div>
        <div className={this.getModulesClassNames()}>
          {modules}
        </div>
      </div>
    );
  }
}

ProjectSidebarListItem.propTypes = {
  repo: React.PropTypes.array,
  project: React.PropTypes.object
};

export default ProjectSidebarListItem;
