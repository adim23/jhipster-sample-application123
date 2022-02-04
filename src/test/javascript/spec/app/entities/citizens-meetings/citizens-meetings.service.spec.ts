/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import CitizensMeetingsService from '@/entities/citizens-meetings/citizens-meetings.service';
import { CitizensMeetings } from '@/shared/model/citizens-meetings.model';

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
  describe('CitizensMeetings Service', () => {
    let service: CitizensMeetingsService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new CitizensMeetingsService();
      currentDate = new Date();
      elemDefault = new CitizensMeetings(123, currentDate, 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            meetDate: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a CitizensMeetings', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            meetDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            meetDate: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a CitizensMeetings', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a CitizensMeetings', async () => {
        const returnedFromService = Object.assign(
          {
            meetDate: dayjs(currentDate).format(DATE_FORMAT),
            agenda: 'BBBBBB',
            comments: 'BBBBBB',
            amount: 1,
            quantity: 1,
            status: 'BBBBBB',
            flag: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            meetDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a CitizensMeetings', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a CitizensMeetings', async () => {
        const patchObject = Object.assign(
          {
            agenda: 'BBBBBB',
            comments: 'BBBBBB',
            amount: 1,
            status: 'BBBBBB',
            flag: true,
          },
          new CitizensMeetings()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            meetDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a CitizensMeetings', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of CitizensMeetings', async () => {
        const returnedFromService = Object.assign(
          {
            meetDate: dayjs(currentDate).format(DATE_FORMAT),
            agenda: 'BBBBBB',
            comments: 'BBBBBB',
            amount: 1,
            quantity: 1,
            status: 'BBBBBB',
            flag: true,
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            meetDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of CitizensMeetings', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a CitizensMeetings', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a CitizensMeetings', async () => {
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
