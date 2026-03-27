import TaskItem from "../TaskItem.tsx";
import {render, screen} from "@testing-library/react";
import {expect} from "vitest";
import type {Task} from "../TaskType.ts";


describe('Task Item', () => {
    it('should display task item', () => {
        const task1: Task = {'id': 1, 'title': 'First Task', 'description': 'get task component built.'};
        // Arrange
        render(<TaskItem initialTask={task1}/>);

        expect(screen.getByRole('listitem', {name:/task/i})).toBeInTheDocument();
        expect(screen.getByText(/First Task/i, {exact:false})).toBeInTheDocument();
    })
})