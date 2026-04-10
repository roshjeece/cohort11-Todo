import { HttpResponse, http } from 'msw';
import { setupServer } from 'msw/node';
import { beforeAll, expect } from 'vitest';
import {axiosGetAllTasks, axiosSaveTask, getAllTasks} from '../TaskService.ts';
import type { Task } from '../TaskType.ts';

describe('Task Service', () => {
  //axios.defaults.baseURL = "http://localhost:8080";

  const server = setupServer();
  beforeAll(() => server.listen());
  afterAll(() => server.close());
  afterEach(() => server.resetHandlers());

  it('should get all tasks', async () => {
    const expected: Task[] = [
      { id: 1, title: 'First Task', description: 'get task component built.' },
      { id: 2, title: 'Second Task', description: 'use new task component.' },
    ];

    server.use(
      http.get('/api/v1/task', () =>
        HttpResponse.json(expected, { status: 200 }),
      ),
    );

    expect(await axiosGetAllTasks()).toStrictEqual(expected);
    expect(await getAllTasks()).toStrictEqual(expected);
  });

  it('should save a new task', async () => {
    const newTask: Task = {title: 'Feed dog', description: 'Please feed the dog'}
    const savedTask: Task = { id: 1, title: 'Feed dog', description: 'Please feed the dog' };
    server.use(
        http.post('/api/v1/task', () =>
          HttpResponse.json(savedTask, {status: 201}),
            ),
        );

    expect(await axiosSaveTask(newTask)).toStrictEqual(savedTask);
  })
});