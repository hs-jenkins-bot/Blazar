window.config = {
  appRoot: ''
};

jest.dontMock('../../fixtures/variousBuilds');
jest.dontMock('../../collections/Builds');
jest.dontMock('underscore');

jest.dontMock('../../collections/BaseCollection');
jest.dontMock('../../collections/Collection');

import variousBuilds from '../../fixtures/variousBuilds';
import Builds from '../../collections/Builds';

describe('Builds Collection', () => {

  let builds;

  beforeEach(() => {
    builds = new Builds();
    builds.set(variousBuilds);
  });

  it('begins the offset at 0', () => {
    expect(builds.updatedTimestamp).toEqual(0);
  });

  it('has a build state', () => {
    const buildState = builds._hasBuildState();
    expect(buildState.length).toEqual(2);
  });

  it('filters builds by host', () => {
    const orgBuilds = builds._OrgBuildsByHost('github.com');
    expect(orgBuilds.length).toEqual(1);
  });

  it('gets hosts with orgs', () => {
    const hosts = builds.getHosts();
    const mockHosts = [
      { name: 'github.com', orgs: [{blazarPath: '/builds/github.com/HubSpot', name: 'HubSpot'}] },
      { name: 'git.something.com', orgs: [{blazarPath: '/builds/git.something.com/HubSpot', name: 'HubSpot'}] }
    ];
    expect(hosts).toEqual(mockHosts);
  });


});
