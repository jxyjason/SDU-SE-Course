from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time

if __name__ == "__main__":
    driver_path = r'C:/Program Files/Google/Chrome/Application/chromedriver.exe'

    driver = webdriver.Chrome(executable_path=driver_path)

    driver.get('http://127.0.0.1:9999/login')

    username_input = driver.find_element(By.NAME, 'username')
    password_input = driver.find_element(By.NAME, 'password')
    username_input.send_keys('admin')
    password_input.send_keys('admin123')
    password_input.send_keys(Keys.ENTER)


    time.sleep(5)

    driver.get('http://127.0.0.1:9999/system/user')

    tbody = driver.find_element(By.XPATH, './/tbody')

    rows = tbody.find_elements(By.XPATH, './/tr')
    cols = []
    for row in rows:
        cols= cols +row.find_elements(By.XPATH, './/td')
    for col in cols:
        print(col.text)

    driver.quit()
