document.addEventListener("DOMContentLoaded", function () {
  const createHabitForm = document.getElementById("createHabitForm");

  if (!createHabitForm) {
    console.error("Form with id 'createHabitForm' not found.");
    return;
  }

  const elements = {
    habitName: document.getElementById("habitName"),
    startDate: document.getElementById("startDate"),
    trackIn: document.getElementById("trackIn"),
    value: document.getElementById("value"),
    reminder: document.getElementById("reminder"),
    doAt: document.getElementById("doAt"),
    customParam: document.getElementById("customParam"),
    customValue: document.getElementById("customValue"),
    hoursInput: document.getElementById("hoursInput"),
    minutesInput: document.getElementById("minutesInput"),
    timeInput: document.getElementById("timeInput")
  };

  elements.trackIn.addEventListener('change', handleTrackInChange);

  createHabitForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const habitName = elements.habitName?.value.trim() || "";
    const startDate = elements.startDate?.value || "";
    let trackIn = elements.trackIn?.value || "";
    let value = elements.value?.value.trim() || "";
    const reminder = elements.reminder?.checked || false;
    let hours = null;
    let minutes = null;
    const time = elements.timeInput?.value || "";

    if (trackIn === 'Customize') {
      trackIn = elements.customParam?.value.trim() || "";
      value = elements.customValue?.value.trim() || "";
    } else if (trackIn === 'Duration') {
      if (validateTimeInputs()) {
        hours = parseInt(elements.hoursInput.value);
        minutes = parseInt(elements.minutesInput.value);
        value = `${hours} hours ${minutes} minutes`;
      } else {
        alert("Invalid time inputs. Please correct them.");
        return;
      }
    } else if (trackIn === 'Cups' || trackIn === 'Litres' || trackIn === 'Steps' || trackIn === 'DISTANCE') {
      value = elements.value?.value.trim() || "";
    } else if (trackIn !== "") {
      // Alert only if an invalid non-empty trackIn option is selected
      alert("Unknown tracking option. Please select a valid option.");
      return;
    } else {
      // If 'trackIn' is not selected, proceed without it
      trackIn = null; // Set to null to indicate no tracking
      value = null;   // Also set value to null
    }

    const doAt = (elements.doAt?.value.split(',') || []).map(value => value.toUpperCase());
    console.log('Selected doAt values:', doAt);

    const selectedDays = Array.from(document.querySelectorAll('input[name="days"]:checked')).map(day => day.value.toUpperCase());
    console.log('Selected days:', selectedDays);

    const selectedWeek = Array.from(document.querySelectorAll('input[name="week"]:checked')).map(week => week.value.toUpperCase());
    console.log('Selected week:', selectedWeek);

    const selectedMonths = Array.from(document.querySelectorAll('input[name="months"]:checked')).map(month => month.value.toUpperCase());
    console.log('Selected months:', selectedMonths);

    // Construct the request object without createdAt field
    const habitRequest = {
      habitName,
      startDate,
      trackIn,
      value,
      reminder,
      doAt,
      selectedDays,
      selectedWeek,
      selectedMonths,
      hours,
      minutes,
      time
    };

    console.log("Request Data:", habitRequest);

    fetch("http://localhost:8080/api/habit", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(habitRequest)
    })
    .then(response => {
      if (!response.ok) {
        return response.text().then(text => {
          throw new Error("Network response was not ok: " + text);
        });
      }
      return response.json();
    })
    .then(data => {
      alert("Habit created successfully: " + data.habitName);
      window.location.href = "index.html"; // Redirect to the index page
    })
    .catch(error => {
      console.error("There was a problem with the fetch operation:", error);
      alert("Error creating habit: " + error.message);
    });    
  });
});

function handleTrackInChange() {
  const trackInSelect = document.getElementById('trackIn');
  const valueInput = document.getElementById('value');
  const valueContainer = document.getElementById('valueContainer');
  const customizeContainer = document.getElementById('customizeContainer');
  const timeContainer = document.getElementById('timeContainer');

  valueContainer.style.display = 'none';
  timeContainer.style.display = 'none';
  customizeContainer.style.display = 'none';
  valueInput.value = '';

  const selectedOption = trackInSelect.value;

  if (selectedOption === 'Duration') {
    timeContainer.style.display = 'block';
  } else if (selectedOption === 'Cups' || selectedOption === 'Litres' || selectedOption === 'Steps' || selectedOption === 'DISTANCE') {
    valueContainer.style.display = 'block';
    valueInput.placeholder = `Enter ${selectedOption.toLowerCase()}`;
    valueInput.type = 'number';
    valueInput.min = '1';
    if (selectedOption === 'Litres') {
      valueInput.step = '0.1';
      valueInput.min = '0.1';
    } else {
      valueInput.step = '1';
    }
  } else if (selectedOption === 'Customize') {
    customizeContainer.style.display = 'block';
  }
}

function validateTimeInputs() {
  const hoursInput = document.getElementById('hoursInput');
  const minutesInput = document.getElementById('minutesInput');

  let isValid = true;

  if (hoursInput.value < 0 || hoursInput.value > 23) {
    isValid = false;
    hoursInput.setCustomValidity('Please enter a valid hour (0-23).');
  } else {
    hoursInput.setCustomValidity('');
  }

  if (minutesInput.value < 0 || minutesInput.value > 59) {
    isValid = false;
    minutesInput.setCustomValidity('Please enter valid minutes (0-59).');
  } else {
    minutesInput.setCustomValidity('');
  }

  hoursInput.reportValidity();
  minutesInput.reportValidity();

  return isValid;
}
