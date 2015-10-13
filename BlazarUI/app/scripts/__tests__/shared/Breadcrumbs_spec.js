jest.dontMock('../../components/header/Breadcrumbs.jsx');
jest.dontMock('../../components/shared/Icon.jsx');
jest.dontMock('../../components/shared/Logo.jsx');
jest.dontMock('../../components/header/HostDropdownBreadcrumb.jsx');
jest.dontMock('react-router');
jest.dontMock('classnames');
jest.dontMock('underscore');
jest.dontMock('../../test-utils/helpers');

import React from 'react/addons';
const TestUtils = React.addons.TestUtils;
import Breadcrumbs from '../../components/header/Breadcrumbs.jsx';

// To do: pull this in from test-utilds
// import {renderedOutput} from '../../test-utils/helpers';
function renderedOutput(elt) {
  const shallowRenderer = TestUtils.createRenderer();
  shallowRenderer.render(elt);
  return shallowRenderer.getRenderOutput();
}


describe('<Breadcrumbs />', () => {

  let breadcrumbs;
  const appRoot = '';

  const params = {
    branch: 'branch',
    host: 'github.com',
    module: 'module',
    org: 'org',
    repo: 'repo'
  };

  beforeEach(() => {
    breadcrumbs = renderedOutput(
      <Breadcrumbs
        appRoot={appRoot}
        params={params}
        hosts={[]}
        loadingHost={false}
      />
    );
  });

  it('should have the correct class names', () => {
    expect(breadcrumbs.props.className).toBe('breadcrumbs');
  });

  // To do:
  // it('should render all the children', () => {
  // });

});
