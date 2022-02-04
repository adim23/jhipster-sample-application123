/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import CitiesService from '@/entities/cities/cities.service';
import { Cities } from '@/shared/model/cities.model';

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
  describe('Cities Service', () => {
    let service: CitiesService;
    let elemDefault;

    beforeEach(() => {
      service = new CitiesService();
      elemDefault = new Cities(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
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

      it('should create a Cities', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Cities', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Cities', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            president: 'BBBBBB',
            presidentsPhone: 'BBBBBB',
            secretary: 'BBBBBB',
            secretarysPhone: 'BBBBBB',
            police: 'BBBBBB',
            policesPhone: 'BBBBBB',
            doctor: 'BBBBBB',
            doctorsPhone: 'BBBBBB',
            teacher: 'BBBBBB',
            teachersPhone: 'BBBBBB',
            priest: 'BBBBBB',
            priestsPhone: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Cities', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Cities', async () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            police: 'BBBBBB',
            policesPhone: 'BBBBBB',
            doctor: 'BBBBBB',
            teacher: 'BBBBBB',
            teachersPhone: 'BBBBBB',
          },
          new Cities()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Cities', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Cities', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            president: 'BBBBBB',
            presidentsPhone: 'BBBBBB',
            secretary: 'BBBBBB',
            secretarysPhone: 'BBBBBB',
            police: 'BBBBBB',
            policesPhone: 'BBBBBB',
            doctor: 'BBBBBB',
            doctorsPhone: 'BBBBBB',
            teacher: 'BBBBBB',
            teachersPhone: 'BBBBBB',
            priest: 'BBBBBB',
            priestsPhone: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Cities', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Cities', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Cities', async () => {
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
