const body = document.querySelector('body'),
      sidebar = body.querySelector('nav'),
      toggle = body.querySelector(".toggle"),
      searchBtn = body.querySelector(".search-box"),
      modeSwitch = body.querySelector(".toggle-switch"),
      modeText = body.querySelector(".mode-text");


toggle.addEventListener("click" , () =>{
    sidebar.classList.toggle("close");
})

// searchBtn.addEventListener("click" , () =>{
//     sidebar.classList.remove("close");
// })

modeSwitch.addEventListener("click" , () =>{
    body.classList.toggle("dark");
    
    if(body.classList.contains("dark")){
        modeText.innerText = "Light mode";
    }else{
        modeText.innerText = "Dark mode";
        
    }
});


async function createBatch() {
    const batchName = $('#batchname').val();
    const startDate = $('#sdate').val();
    const endDate = $('#edate').val();
    const trainer1 = $('#t1').val();
    const trainer2 = $('#t2').val();
    const studentCount = $('#scount').val();
    const testing = $('#test').val();
    const fullstack = $('#full').val();
 
        const url = 'http://localhost:8080/create-batch ';
        const result = await fetch(url, {method:'POST', body: JSON.stringify({
            batchName:batchName,
            startDate:startDate,
            endDate:endDate,
            trainer1:trainer1,
            trainer2:trainer2,
            studentCount: studentCount,
            testing:testing,
            fullstack:fullstack
    
        }), headers: {"Content-Type": "application/json" } });
    
        const finalData = await result.json();

       $('#dt').html( "Created " + finalData.batchName  + " batch");
           
       
       document.addEventListener("DOMContentLoaded", function() {
           console.log("JavaScript code is executing.");
        fetch('http://localhost:8080/get-batchname')
            .then(response => response.json())
            .then(data => {
                const batchButtonsDiv = document.getElementById("batchButtons");
                data.forEach(batchName => {
                    const batchButton = document.createElement("button");
                    batchButton.textContent = batchName;
                    batchButton.onclick = function() {
                        // Handle button click (e.g., navigate to a specific batch page)
                    };
                    batchButtonsDiv.appendChild(batchButton);
                });
            });
    });
    
    }






function toggleCustomDays(option) {
    const popup = document.getElementById('popup');
    if (option === 'custom') {
        popup.classList.remove('hidden');
    } else {
        popup.classList.add('hidden');
    }
}

function closePopup() {
    document.getElementById('popup').classList.add('hidden');
}

function saveSelection() {
    const selectedDays = Array.from(document.querySelectorAll('#popup input[type="checkbox"][name="days"]:checked')).map(cb => cb.value);
    const selectedMonths = Array.from(document.querySelectorAll('#popup input[type="checkbox"][name="months"]:checked')).map(cb => cb.value);

    console.log("Selected Days: ", selectedDays);
    console.log("Selected Months: ", selectedMonths);

    closePopup(); // This should close the popup
}





let currentPopup = null;

function handleTrackInChange() {
    const selectedOption = document.getElementById("trackIn").value;
    const popupMap = {
        'duration': 'durationPopup',
        'litre': 'litrePopup',
        'cups': 'cupsPopup',
        'customize': 'customizePopup'
    };

    const newPopup = popupMap[selectedOption];
    if (currentPopup !== newPopup) {
        if (currentPopup) {
            closePopup(currentPopup);
        }
        openPopup(newPopup);
        currentPopup = newPopup;
    }
}

function openPopup(popupId) {
    document.getElementById(popupId).classList.remove('hidden');
}

function closePopup(popupId) {
    document.getElementById(popupId).classList.add('hidden');
}

  function saveDuration() {
    const duration = document.getElementById('durationInput').value;
    // Save or process the duration value as needed
    closePopup('durationPopup');
  }
  
  function saveLitre() {
    const litre = document.getElementById('litreInput').value;
    // Save or process the litre value as needed
    closePopup('litrePopup');
  }
  
  function saveCups() {
    const cups = document.getElementById('cupsInput').value;
    // Save or process the cups value as needed
    closePopup('cupsPopup');
  }
  
  function saveCustom() {
    const factor = document.getElementById('customFactorInput').value;
    const value = document.getElementById('customValueInput').value;
    // Save or process the custom factor and value as needed
    closePopup('customizePopup');
  }
