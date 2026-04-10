import { render, screen, within } from '@testing-library/react';
import { expect } from 'vitest';
import { TaskPage } from '../TaskPage';
import * as taskApi from '../TaskService.ts';

vi.mock('../TaskService.ts');

const mockData = [
  { id: 1, title: 'First Task', description: 'get task component built.' },
  { id: 2, title: 'Second Task', description: 'use new task component.' },
];

describe('Task Page', () => {
  beforeEach(() => {
    vi.clearAllMocks();
    vi.mocked(taskApi.axiosGetAllTasks).mockResolvedValue(mockData);
  });

  it('should display task heading', async () => {
    render(<TaskPage />);
    await screen.findByRole('list');

    expect(
    screen.getByRole('heading', { name: /Task List/i }),
    ).toBeInTheDocument();
  });

  it('should show multiple tasks', async () => {
    render(<TaskPage />);

    // Wait for async data to render
    const list = await screen.findByRole('list');

    const items = within(list).getAllByRole('listitem');

    expect(items).toHaveLength(2);
    expect(items[0]).toHaveTextContent('First Task');
    expect(items[1]).toHaveTextContent('Second Task');
  });

  it('should show multiple tasks and find the first task', async () => {
    render(<TaskPage />);

    // Wait for async data to render
    const list = await screen.findByRole('list');

    const items = within(list).getAllByRole('listitem');

    expect(items).toHaveLength(2);

    const firstItem = await within(list).findByLabelText('Task 1');
    expect(firstItem).toBeInTheDocument();
  });
});
