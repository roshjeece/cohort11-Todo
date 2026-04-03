import type {Task} from "../TaskType.ts";
import {http, HttpResponse} from "msw";
import {setupServer} from "msw/node";
import {afterAll, beforeAll, afterEach} from "vitest";
import {axiosGetAllTasks, getAllTasks} from "../TaskService.ts";

describe('Task Service', () => {

    const server = setupServer();
    beforeAll(() => server.listen());
    afterAll(() => server.close());
    afterEach(() => server.resetHandlers());

    it('should get all tasks', async () => {
        const expected: Task[] = [
            {'id': 1, 'title': 'First Task', 'description': 'get task component built.'},
            {'id': 2, 'title': 'Second Task', 'description': 'use new component built.'},
            {'id': 3, 'title': 'Third Task', 'description': 'the third component.'}
        ];

        server.use(http.get('api/v1/task/', () =>
            HttpResponse.json(expected, {status: 200})))

        expect(await getAllTasks()).toStrictEqual(expected);
        expect(await axiosGetAllTasks()).toStrictEqual(expected);

    })
});