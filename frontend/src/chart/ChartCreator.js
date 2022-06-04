import Chart from "@/chart/Chart";

class ChartCreator {
    #chart

    createMxM(ctx, stats) {
        this.#destroy()

        this.#chart = new Chart(ctx, {
            data: {
                datasets: [{
                    type: 'bar',
                    yAxisID: 'B',
                    label: 'Movie count',
                    backgroundColor: 'rgb(31,124,217, 0.3)',
                    data: stats.movieData
                },{
                    type: 'line',
                    yAxisID: 'A',
                    label: 'Covid cases',
                    backgroundColor: 'rgb(217,31,31)',
                    borderColor: 'rgb(217,31,31)',
                    data: stats.cases
                },{
                    type: 'line',
                    yAxisID: 'A',
                    label: 'Covid deaths',
                    backgroundColor: 'rgb(0, 0, 0)',
                    borderColor: 'rgb(0, 0, 0)',
                    data: stats.deaths
                }],
                labels: stats.labels
            },
            scales: {
                yAxes: [{
                    id: 'A',
                    type: 'logarithmic',
                    position: 'left'
                },{
                    id: 'B',
                    type: 'linear',
                    position: 'right',
                }]
            },
            options: {
                maintainAspectRatio: false,
            }
        });
    }

    createMxY(ctx, stats) {
        this.#destroy()

        stats.labels.splice(0, 12)
        stats.cases.splice(0, 12)
        stats.deaths.splice(0, 12)
        stats.movieData = ChartCreator.#expandArray(stats.labels, stats.movieData)

        this.#chart = new Chart(ctx, {
            data: {
                datasets: [{
                    type: 'bar',
                    yAxisID: 'B',
                    label: 'Movie count',
                    backgroundColor: 'rgb(31,124,217, 0.3)',
                    data: stats.movieData
                },{
                    type: 'line',
                    yAxisID: 'A',
                    label: 'Covid cases',
                    backgroundColor: 'rgb(217,31,31)',
                    borderColor: 'rgb(217,31,31)',
                    data: stats.cases
                },{
                    type: 'line',
                    yAxisID: 'A',
                    label: 'Covid deaths',
                    backgroundColor: 'rgb(0, 0, 0)',
                    borderColor: 'rgb(0, 0, 0)',
                    data: stats.deaths
                }],
                labels: stats.labels
            },
            scales: {
                yAxes: [{
                    id: 'A',
                    type: 'logarithmic',
                    position: 'left'
                },{
                    id: 'B',
                    type: 'linear',
                    position: 'right',
                }]
            },
            options: {
                maintainAspectRatio: false,
            }
        });
    }

    createStackMxY(ctx, stats) {
        this.#destroy()

        stats.labels.splice(0, 12)
        stats.cases.splice(0, 12)
        stats.deaths.splice(0, 12)

        for (let i = 0; i < stats.movieData.length; i++) {
            stats.movieData[i].data = ChartCreator.#expandArray(stats.labels, stats.movieData[i].data)
        }

        this.#chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: stats.labels,
                datasets: [
                    ...stats.movieData,
                    {
                        type: 'line',
                        yAxisID: 'A',
                        label: 'Covid cases',
                        backgroundColor: 'rgb(217,31,31)',
                        borderColor: 'rgb(217,31,31)',
                        data: stats.cases
                    },{
                        type: 'line',
                        yAxisID: 'A',
                        label: 'Covid deaths',
                        backgroundColor: 'rgb(0, 0, 0)',
                        borderColor: 'rgb(0, 0, 0)',
                        data: stats.deaths
                    }
                ],
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        stacked: true,
                    },
                    y: {
                        stacked: true
                    },
                },
                maintainAspectRatio: false,
            }
        })
    }

    #destroy() {
        if (this.#chart !== null && this.#chart !== undefined) {
            this.#chart.destroy();
        }
    }

    static #expandArray(labels, movieArray) {
        let movieIndex = 0
        let array = []

        for (let i = 0; i < labels.length; i++) {
            var month = labels[i].split(".")[0];
            if (month === "01") {
                array[i] = movieArray[movieIndex]
                movieIndex++
            } else {
                array[i] = 0
            }
        }
        return array
    }
}

export default new ChartCreator()