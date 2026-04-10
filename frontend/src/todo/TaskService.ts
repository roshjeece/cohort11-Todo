import axios, { type AxiosResponse } from 'axios';
import type { Task } from './TaskType.ts';

type GetTasks = () => Promise<Task[]>;
type AxiosGetTasks = () => Promise<Task[]>;
type AxiosSaveTask = (task: Task) => Promise<Task>;

export const getAllTasks: GetTasks = async () => {
  return fetch('/api/v1/task', { method: 'GET' }).then((Response) => {
    return Response.json();
  });
};

export const axiosGetAllTasks: AxiosGetTasks = async () =>
  axios
    .get('/api/v1/task')
    .then((r: AxiosResponse<Task[]>) => r.data)
    .catch();

export const axiosSaveTask: AxiosSaveTask = (task: Task) => (
  axios.post('api/v1/task', task)
      .then((r: AxiosResponse<Task>) => r.data)
      .catch((error) => { throw error; })
)