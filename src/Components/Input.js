import React from "react";

class Input extends React.Component {

    constructor(props) {
        super(props);
        console.log(localStorage);
        if(localStorage.state === undefined){
            this.state = {
                class: '',
                grades: [
                    {
                        ae: '',
                        score: 0,
                        weight: 0,
                        index: 0,
                    },
                    {
                        ae: '',
                        score: 0,
                        weight: 0,
                        index: 1,
                    },
                    {
                        ae: '',
                        score: 0,
                        weight: 0,
                        index: 2,
                    }
                ],
                total: 0,
                maxGrade: 0,
            }
        } else {
            this.state = JSON.parse(localStorage.state);
        }
    }

    encrypt(data) {
        //TODO: encrypt state being saved in browser
    }

    decrypt(data) {
        //TODO: decrypt state that was saved in browser
    }

    generateGradeInput = () => {
        return (
            <div className="container">
                {
                    this.state.grades.map((grade) => (
                        <div className="row" key={grade.index}>
                            <div className="col">
                                <label htmlFor="assignment-exam">Assignment/Exam</label>
                                <input 
                                    type="text" 
                                    id="assignment-exam" 
                                    name="assignment-exam" 
                                    value={grade.ae}
                                    onChange={(e) => {this.editRow(grade.index, 'ae', e.target.value)}}
                                />
                            </div>
                            <div className="col">
                                <label htmlFor="score">Score</label>
                                <input 
                                    type="text" 
                                    id="score" 
                                    name="score"
                                    value={grade.score}
                                    onChange={(e) => {this.editRow(grade.index, 'score', e.target.value)}}
                                    size="3"
                                />
                            </div>
                            <div className="col">
                                <label htmlFor="weight">Weight</label>
                                <input 
                                    type="text" 
                                    id="weight" 
                                    name="weight"
                                    value={grade.weight}
                                    onChange={(e) => {this.editRow(grade.index, 'weight', e.target.value)}}
                                    size="3"
                                />
                            </div>
                        </div>
                    ))
                }
            </div>
        )
    }

    addRow = () => {
        let newGrades = this.state.grades;
        newGrades.push({
            ae: '',
            score: '',
            weight: '', 
            index: this.state.grades.length,
        })
        this.setState({
            grades: newGrades,
        })
        this.saveStateToDevice();
    }

    removeRow = () => {
        let newGrades = this.state.grades;
        newGrades.pop();
        this.setState({
            grades:newGrades,
        })
        this.saveStateToDevice();
    }

    editRow(index, key, value) {
        let newGrades = this.state.grades;
        newGrades[index][key] = value;
        this.setState({
            grades: newGrades,
        })
        this.saveStateToDevice();
    }

    saveStateToDevice = () => {localStorage.state = JSON.stringify(this.state)}

    calculate = () => {
        let newTotal = 0;
        let totalPercent = 0;
        this.state.grades.forEach((key) => {
            newTotal += parseFloat(key.score) * parseFloat(key.weight);
            totalPercent += parseFloat(key.weight);
        });
        console.log(totalPercent)
        totalPercent = newTotal + (100 * (1 - totalPercent));
        this.setState({
            total: newTotal.toString(),
            maxGrade: totalPercent.toString(),
        });
        this.saveStateToDevice();
    }

    render() {
        return (
            <div>
                <fieldset>
                    <legend>
                        Enter your grades
                    </legend>
                    {this.generateGradeInput()}
                    <div className="btn-group" role="group">
                        <input type="submit" onClick={() => this.calculate()} value="Calculate" className="btn btn-primary" />
                        <input type="button" onClick={() => this.addRow()} value="Add Row" className="btn btn-secondary" />
                        <input type="button" onClick={() => this.removeRow()} value="Remove Row" className="btn btn-secondary"/>
                    </div>
                </fieldset>
                <h3>Total: {this.state.total === 0 ? "None" : this.state.total + "%"}</h3>
                <h3>Maximum Possible Grade: {this.state.maxGrade}</h3>
            </div>
        )
    }
}

export default Input;