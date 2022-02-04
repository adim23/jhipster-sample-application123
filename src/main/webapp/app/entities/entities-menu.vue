<template>
  <div>
    <b-dropdown-item to="/jobs">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.jobs')">Jobs</span>
    </b-dropdown-item>
    <b-dropdown-item to="/countries">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.countries')">Countries</span>
    </b-dropdown-item>
    <b-dropdown-item to="/regions">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.regions')">Regions</span>
    </b-dropdown-item>
    <b-dropdown-item to="/cities">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.cities')">Cities</span>
    </b-dropdown-item>
    <b-dropdown-item to="/zip-codes">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.zipCodes')">Zip Codes</span>
    </b-dropdown-item>
    <b-dropdown-item to="/addresses">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.addresses')">Addresses</span>
    </b-dropdown-item>
    <b-dropdown-item to="/phones">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.phones')">Phones</span>
    </b-dropdown-item>
    <b-dropdown-item to="/emails">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.emails')">Emails</span>
    </b-dropdown-item>
    <b-dropdown-item to="/phone-types">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.phoneTypes')">Phone Types</span>
    </b-dropdown-item>
    <b-dropdown-item to="/contact-types">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.contactTypes')">Contact Types</span>
    </b-dropdown-item>
    <b-dropdown-item to="/social-kinds">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.socialKinds')">Social Kinds</span>
    </b-dropdown-item>
    <b-dropdown-item to="/social-contacts">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.socialContacts')">Social Contacts</span>
    </b-dropdown-item>
    <b-dropdown-item to="/company-kinds">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.companyKinds')">Company Kinds</span>
    </b-dropdown-item>
    <b-dropdown-item to="/companies">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.companies')">Companies</span>
    </b-dropdown-item>
    <b-dropdown-item to="/codes">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.codes')">Codes</span>
    </b-dropdown-item>
    <b-dropdown-item to="/teams">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.teams')">Teams</span>
    </b-dropdown-item>
    <b-dropdown-item to="/origins">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.origins')">Origins</span>
    </b-dropdown-item>
    <b-dropdown-item to="/citizen-folders">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.citizenFolders')">Citizen Folders</span>
    </b-dropdown-item>
    <b-dropdown-item to="/marital-status">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.maritalStatus')">Marital Status</span>
    </b-dropdown-item>
    <b-dropdown-item to="/citizens">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.citizens')">Citizens</span>
    </b-dropdown-item>
    <b-dropdown-item to="/citizens-relations">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.citizensRelations')">Citizens Relations</span>
    </b-dropdown-item>
    <b-dropdown-item to="/citizens-meetings">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.citizensMeetings')">Citizens Meetings</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
