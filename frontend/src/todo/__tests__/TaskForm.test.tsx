import {render, screen} from '@testing-library/react';
import TaskForm from '../TaskForm.tsx';
import {userEvent} from "@testing-library/user-event";
import * as taskApi from "../TaskService.ts"

vi.mock('../TaskService.ts');

describe('Task Form', () => {
    const user = userEvent.setup();
    it('should display form heading', () => {
        render(<TaskForm/>);
        expect(screen.getByRole('heading', {name: /Task Form/i})).toBeInTheDocument();
        expect(screen.getByLabelText(/Title/i)).toBeInTheDocument();
        expect(screen.getByRole('textbox', {name: /title/i})).toBeInTheDocument()
        expect(screen.getByRole('textbox', {name: /description/i})).toBeInTheDocument()
        expect(screen.getByRole('button', {name: /add task/i})).toBeInTheDocument();
        screen.logTestingPlaygroundURL();
    });

    describe('Mock Task Form', () => {
        const mockData =
            {id: 1, title: 'Feed dog', description: 'Please feed the dog'}

        beforeEach(() => {
            vi.clearAllMocks();
        });
        afterEach(() => {
            vi.restoreAllMocks()
        })

        it('should be able to input into fields and click submit', async () => {
            vi.mocked(taskApi.axiosSaveTask).mockResolvedValue(mockData);

            render(<TaskForm/>);
            const title = screen.getByRole('textbox', {name: /title/i});
            const description = screen.getByRole('textbox', {name: /description/i});
            const submit = screen.getByRole('button', {name: /add task/i});

            await user.type(title, 'new title');
            expect(title).toHaveValue('new title');

            await user.type(description, 'new description');
            expect(description).toHaveValue('new description');

            await user.click(submit);
            expect(taskApi.axiosSaveTask).toHaveBeenCalledOnce();
        })

        it('should save a new task on submit', async () => {
            vi.mocked(taskApi.axiosSaveTask).mockResolvedValue({
                id: 1,
                title: 'new title',
                description: 'new description'
            });

            render(<TaskForm/>);

            await user.type(screen.getByLabelText(/title/i), 'new title');
            await user.type(screen.getByLabelText(/description/i), 'new description');
            await user.click(screen.getByRole('button', {name: /add task/i}));

            expect(taskApi.axiosSaveTask).toHaveBeenCalledWith({
                title: 'new title',
                description: 'new description'
            });
        });
    })


})