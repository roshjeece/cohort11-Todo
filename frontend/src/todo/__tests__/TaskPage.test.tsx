import TaskPage from "../TaskPage.tsx";
import {render, screen, within} from "@testing-library/react";
import {expect} from "vitest";
import type {Task} from "../TaskType.ts";

describe('Task Page', () => {
    it('should display title', () => {
        render(<TaskPage/>)

        expect(screen.getByRole('heading', {name: /Task List/i})).toBeInTheDocument()
    })

    it('should show multiple tasks', () => {
        const task1: Task = {'id': 1, 'title': 'First Task', 'description': 'get task component built.'};
        const task2: Task = {'id': 2, 'title': 'Second Task', 'description': 'use new component built.'};
        const task3: Task = {'id': 3, 'title': 'Third Task', 'description': 'the third component.'};

        render(<TaskPage/>);
        const list = screen.getByRole('list');
        expect(within(list).getAllByRole('listitem')).toHaveLength(3)


    })
});
