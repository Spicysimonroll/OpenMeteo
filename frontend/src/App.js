import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import {
	Chart as ChartJS,
	BarElement,
	CategoryScale,
	LinearScale,
	Tooltip,
	Legend
} from "chart.js";
import "./App.css"; // Import external CSS

ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

function App() {
	const [cities, setCities] = useState([]);
	const [selectedCity, setSelectedCity] = useState("");
	const [weatherData, setWeatherData] = useState(null);
	const [error, setError] = useState(null);

	useEffect(() => {
		fetch("http://localhost:8081/api/cities")
		.then((res) => res.json())
		.then(setCities)
		.catch(() => setError("Failed to load cities."));
	}, []);

	const handleCityChange = (e) => {
		setSelectedCity(e.target.value);
		setWeatherData(null);
		setError(null);
	};

	const fetchWeather = () => {
		if (!selectedCity) return;

		fetch(`http://localhost:8081/api/weather?city=${selectedCity}`)
		.then((res) => {
			if (!res.ok) throw new Error("City not found");
			return res.json();
		})
		.then(setWeatherData)
		.catch(() => setError("Failed to load weather data."));
	};

	return (
		<div className="app-container">
		<h2>Weather Dashboard</h2>

		<div className="controls">
			<select value={selectedCity} onChange={handleCityChange}>
			<option value="">Select a city</option>
			{cities.map((city) => (
				<option key={city.id} value={city.name}>
				{city.name}
				</option>
			))}
			</select>

			<button onClick={fetchWeather}>Get Weather</button>
		</div>

		{error && <p className="error-message">{error}</p>}

		{weatherData && (
			<div className="chart-container">
			<h3>Temperature in {selectedCity}</h3>
			<Bar
				data={{
				labels: weatherData.time.slice(0, 24),
				datasets: [
					{
					label: "Temperature (°C)",
					data: weatherData.temperature.slice(0, 24),
					backgroundColor: "rgba(75,192,192,0.6)",
					},
				],
				}}
				options={{
				scales: {
					x: { title: { display: true, text: "Time" } },
					y: { title: { display: true, text: "Temperature (°C)" } },
				},
				}}
			/>
			</div>
		)}
		</div>
	);
}

export default App;
