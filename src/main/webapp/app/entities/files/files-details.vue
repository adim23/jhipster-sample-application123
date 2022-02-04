<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="files">
        <h2 class="jh-entity-heading" data-cy="filesDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.files.detail.title')">Files</span> {{ files.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.uuid')">Uuid</span>
          </dt>
          <dd>
            <span>{{ files.uuid }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.filename')">Filename</span>
          </dt>
          <dd>
            <span>{{ files.filename }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.fileType')">File Type</span>
          </dt>
          <dd>
            <span v-text="$t('jhipsterSampleApplication123App.FileType.' + files.fileType)">{{ files.fileType }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.fileSize')">File Size</span>
          </dt>
          <dd>
            <span>{{ files.fileSize }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.createDate')">Create Date</span>
          </dt>
          <dd>
            <span>{{ files.createDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.filePath')">File Path</span>
          </dt>
          <dd>
            <span>{{ files.filePath }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.version')">Version</span>
          </dt>
          <dd>
            <span>{{ files.version }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.mime')">Mime</span>
          </dt>
          <dd>
            <span>{{ files.mime }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.parent')">Parent</span>
          </dt>
          <dd>
            <div v-if="files.parent">
              <router-link :to="{ name: 'FilesView', params: { filesId: files.parent.id } }">{{ files.parent.filename }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.files.createdBy')">Created By</span>
          </dt>
          <dd>
            {{ files.createdBy ? files.createdBy.login : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="files.id" :to="{ name: 'FilesEdit', params: { filesId: files.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./files-details.component.ts"></script>
