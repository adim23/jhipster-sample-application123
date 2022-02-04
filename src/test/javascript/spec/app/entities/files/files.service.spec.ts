/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import FilesService from '@/entities/files/files.service';
import { Files } from '@/shared/model/files.model';
import { FileType } from '@/shared/model/enumerations/file-type.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Files Service', () => {
    let service: FilesService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new FilesService();
      currentDate = new Date();
      elemDefault = new Files(123, 'AAAAAAA', 'AAAAAAA', FileType.FOLDER, 0, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Files', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            createDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createDate: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Files', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Files', async () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            filename: 'BBBBBB',
            fileType: 'BBBBBB',
            fileSize: 1,
            createDate: dayjs(currentDate).format(DATE_FORMAT),
            filePath: 'BBBBBB',
            version: 'BBBBBB',
            mime: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Files', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Files', async () => {
        const patchObject = Object.assign(
          {
            uuid: 'BBBBBB',
            filename: 'BBBBBB',
            fileType: 'BBBBBB',
            filePath: 'BBBBBB',
          },
          new Files()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Files', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Files', async () => {
        const returnedFromService = Object.assign(
          {
            uuid: 'BBBBBB',
            filename: 'BBBBBB',
            fileType: 'BBBBBB',
            fileSize: 1,
            createDate: dayjs(currentDate).format(DATE_FORMAT),
            filePath: 'BBBBBB',
            version: 'BBBBBB',
            mime: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Files', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Files', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Files', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
