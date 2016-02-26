package com.hubspot.blazar.data.dao;


import java.util.Set;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;

import com.google.common.base.Optional;
import com.hubspot.blazar.base.notifications.InstantMessageConfiguration;
import com.hubspot.rosetta.jdbi.BindWithRosetta;

public interface InstantMessageConfigurationDao {

  @SqlQuery("SELECT * FROM instant_message_configs WHERE active = 1")
  Set<InstantMessageConfiguration> getAll();

  @SingleValueResult
  @SqlQuery("SELECT * FROM instant_message_configs WHERE id = :id")
  Optional<InstantMessageConfiguration> get(@Bind("id") long id);

  @SqlQuery("SELECT * FROM instant_message_configs WHERE branchId = :branchId and moduleId is NULL and active = 1")
  Set<InstantMessageConfiguration> getAllWithBranchId(@Bind("branchId") long branchId);

  @SqlQuery("SELECT * FROM instant_message_configs WHERE moduleId = :moduleId and active = 1")
  Set<InstantMessageConfiguration> getAllWithModuleId(@Bind("moduleId") long moduleId);

  @GetGeneratedKeys
  @SqlUpdate("INSERT INTO instant_message_configs (branchId, moduleId, channelName, onFinish, onFail, onChange, onRecover, active) VALUES (:branchId, :moduleId, :channelName, :onFinish, :onFail, :onChange, :onRecover, :active)")
  long insert(@BindWithRosetta InstantMessageConfiguration instantMessageConfiguration);

  @SqlUpdate("INSERT INTO instant_message_configs (branchId, moduleId, channelName, onFinish, onFail, onChange, onRecover, active) VALUES (:branchId, :moduleId, :channelName, :onFinish, :onFail, :onChange, :onRecover, :active)")
  int update(@BindWithRosetta InstantMessageConfiguration instantMessageConfiguration);

  @SqlUpdate("UPDATE instant_message_configs SET active = 0 WHERE id = :id")
  int delete(@Bind("id") long id);

}
