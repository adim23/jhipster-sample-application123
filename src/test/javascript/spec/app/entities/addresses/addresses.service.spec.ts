/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import AddressesService from '@/entities/addresses/addresses.service';
import { Addresses } from '@/shared/model/addresses.model';

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
  describe('Addresses Service', () => {
    let service: AddressesService;
    let elemDefault;

    beforeEach(() => {
      service = new AddressesService();
      elemDefault = new Addresses(
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
        false
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

      it('should create a Addresses', async () => {
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

      it('should not create a Addresses', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Addresses', async () => {
        const returnedFromService = Object.assign(
          {
            address: 'BBBBBB',
            addressNo: 'BBBBBB',
            zipCode: 'BBBBBB',
            prosfLetter: 'BBBBBB',
            nameLetter: 'BBBBBB',
            letterClose: 'BBBBBB',
            firstLabel: 'BBBBBB',
            secondLabel: 'BBBBBB',
            thirdLabel: 'BBBBBB',
            fourthLabel: 'BBBBBB',
            fifthLabel: 'BBBBBB',
            favourite: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Addresses', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Addresses', async () => {
        const patchObject = Object.assign(
          {
            zipCode: 'BBBBBB',
            prosfLetter: 'BBBBBB',
            secondLabel: 'BBBBBB',
            thirdLabel: 'BBBBBB',
            fifthLabel: 'BBBBBB',
            favourite: true,
          },
          new Addresses()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Addresses', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Addresses', async () => {
        const returnedFromService = Object.assign(
          {
            address: 'BBBBBB',
            addressNo: 'BBBBBB',
            zipCode: 'BBBBBB',
            prosfLetter: 'BBBBBB',
            nameLetter: 'BBBBBB',
            letterClose: 'BBBBBB',
            firstLabel: 'BBBBBB',
            secondLabel: 'BBBBBB',
            thirdLabel: 'BBBBBB',
            fourthLabel: 'BBBBBB',
            fifthLabel: 'BBBBBB',
            favourite: true,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Addresses', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Addresses', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Addresses', async () => {
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
