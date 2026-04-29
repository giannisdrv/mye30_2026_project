import csv
import re
import difflib
import os

def csv_to_tsv(input_file, output_file,input_delimiter):
    with open(input_file, 'r', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile, delimiter=input_delimiter)
        with open(output_file, 'w', encoding='utf-8', newline='') as tsvfile:
            writer = csv.writer(tsvfile, delimiter='\t')
            for row in reader:
                processed_row = [item.strip() for item in row]  
                writer.writerow(processed_row)

def isolate_bad_records_from_inproceedings(input_file, output_file,quarntine_file):
    with open(input_file, 'r', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile, delimiter='\t')
        with open(output_file, 'w', encoding='utf-8', newline='') as tsvfile:
            writer = csv.writer(tsvfile, delimiter='\t')
            with open(quarntine_file, 'w', encoding='utf-8', newline='') as quarantinefile:
                quarantine_writer = csv.writer(quarantinefile, delimiter='\t')
                for row in reader:
                    if (row[0] == '' or row[1] == '' or row[2] == '' or row[19] == '' or row[23] == ''):
                        quarantine_writer.writerow(row)
                    else:
                        writer.writerow([row[0],row[1],row[2],row[6],row[10],row[11],row[15],row[19],row[22],row[23]])

def isolate_bad_records_from_article(input_file, output_file,quarntine_file):
    with open(input_file, 'r', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile, delimiter='\t')
        with open(output_file, 'w', encoding='utf-8', newline='') as tsvfile:
            writer = csv.writer(tsvfile, delimiter='\t')
            with open(quarntine_file, 'w', encoding='utf-8', newline='') as quarantinefile:
                quarantine_writer = csv.writer(quarantinefile, delimiter='\t')
                for row in reader:
                    if (row[0] == '' or row[1] == '' or row[10] == '' or row[23] == '' or row[28] == ''):
                        quarantine_writer.writerow(row)
                    else:
                        writer.writerow([row[0],row[1],row[10],row[11],row[12],row[15],row[16],row[23],row[26],row[27],row[28]])   

def clean_journal_ranking_data(input_file, output_file,quarntine_file):
    with open(input_file, 'r', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile, delimiter='\t')
        with open(output_file, 'w', encoding='utf-8', newline='') as tsvfile:
            writer = csv.writer(tsvfile, delimiter='\t')
            with open(quarntine_file, 'w', encoding='utf-8', newline='') as quarantinefile:
                quarantine_writer = csv.writer(quarantinefile, delimiter='\t')
                for row in reader:
                    if (row[0] == '' or row[1] == '' or row[2] == '' or row[3] == '' or row[4] == '' or row[5] == '' or row[6] == '' or row[7] == ''
                        or row[9] == '' or row[13] == '' or row[14] == '' or row[15] == '' or row[16] == '' or row[17] == '' or row[18] == ''):
                        quarantine_writer.writerow(row)
                    else:
                        writer.writerow([row[0],row[1],row[2],row[3],row[4],row[5],row[6],row[7],row[9],row[13],row[14],row[15],row[16],row[17],row[18]])

def final_article(input_file1, input_file2, output_file): 
    all_articles = []
    with open(input_file1, 'r', encoding='utf-8') as f1:
        reader1 = csv.reader(f1, delimiter='\t')
        next(reader1) 
        for row in reader1:
            unified_row = [row[0], row[1], row[2], 'null', row[4], row[5], row[6], row[7], row[8], row[9]]
            all_articles.append(unified_row)
    with open(input_file2, 'r', encoding='utf-8') as f2:
        reader2 = csv.reader(f2, delimiter='\t')
        next(reader2)
        for row in reader2:
            unified_row = [row[0], row[1], 'null', row[2], row[3], row[4], row[6], row[7], row[8], row[10]]
            all_articles.append(unified_row)
    all_articles.sort(key=lambda x: int(x[0])) 
    with open(output_file, 'w', encoding='utf-8', newline='') as output:
        writer = csv.writer(output, delimiter='\t')
        writer.writerow(['id','author','booktitle','journal','key','mdate','pages','title','url','year'])
        writer.writerows(all_articles)

def id_for_best_subject_area(input_file,output_file):
    with open(input_file, 'r', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile, delimiter='\t')
        next(reader)
        with open(output_file, 'w', encoding='utf-8', newline='') as tsvfile:
            writer = csv.writer(tsvfile, delimiter='\t')
            writer.writerow(['id','name'])
            id_counter = 1
            for row in reader:
                name = row[0].strip()
                if name != '':
                    writer.writerow([id_counter,name])
                    id_counter += 1

def map_id_to_acronym(input_file):
    conference_dic = {}
    with open(input_file, 'r', encoding='utf-8') as f:
        reader = csv.reader(f, delimiter='\t')
        next(reader)
        for row in reader:
            conference_id = row[0].strip()
            conference_acronym = row[2].strip()
            conference_dic[conference_acronym] = conference_id
    return conference_dic

def map_id_to_best_subject_area(input_file):
    subject_area_dic = {}
    with open(input_file, 'r', encoding='utf-8') as f:
        reader = csv.reader(f, delimiter='\t')
        next(reader)
        for row in reader:
            subject_area_id = row[0].strip()
            subject_area_name = row[1].strip()
            subject_area_dic[subject_area_name] = subject_area_id
    return subject_area_dic

def map_id_to_journal_and_bestsubjectarea(input_file, output_file,best_subject_area_dic):
    journal_dic = {}
    with open(input_file, 'r', encoding='utf-8') as f:
        reader = csv.reader(f, delimiter='\t')
        columns = next(reader)
        with open(output_file, 'w', encoding='utf-8', newline='') as out_file:
            writer = csv.writer(out_file, delimiter='\t')
            writer.writerow(['id']+columns)
            id = 1
            for row in reader:
                bestsubjectarea_string = row[8].strip()
                row[8] = best_subject_area_dic.get(bestsubjectarea_string, '\\N')
                writer.writerow([id]+row)
                journal_title = row[1].strip()
                journal_dic[journal_title] = id
                id += 1
    return journal_dic

def process_conference_data(input_file, output_file, input_delimiter=','):
    seen_ids = set()
    with open(input_file, 'r', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile, delimiter=input_delimiter)
        
        with open(output_file, 'w', encoding='utf-8', newline='') as tsvfile:
            for row in reader:
                if not row or len(row) < 7:
                    continue

                conf_id = row[0].strip()

                if conf_id not in seen_ids:
                    seen_ids.add(conf_id)
                    filtered_row = [
                        row[0].replace('"', '').strip(),
                        row[1].replace('"', '').strip(),
                        row[2].replace('"', '').strip(),
                        row[4].replace('"', '').strip(),
                        row[6].replace('"', '').strip()
                    ]
                    
                    tsvfile.write('\t'.join(filtered_row) + '\n')

def normalize_title(title):
    temp = title.lower().strip()
    temp = temp.replace('&', 'and')
    if temp.startswith('the '):  
        temp = temp[4:]
    return temp

def final_tables(input_file, conference_dic, journal_dic, article_file, author_file, article_author_file):
    author_dic = {}
    author_id = 1
    normalized_titles = {}
    for title in journal_dic.keys():
        normalized_title = normalize_title(title)
        normalized_titles[normalized_title] = title
    
    journal_titles = {}

    with open(input_file, 'r', encoding='utf-8') as f, \
            open(article_file, 'w', encoding='utf-8', newline='') as article_out, \
            open(author_file, 'w', encoding='utf-8', newline='') as author_out, \
            open(article_author_file, 'w', encoding='utf-8', newline='') as article_author_out:
        reader = csv.reader(f, delimiter='\t')
        article_writer = csv.writer(article_out, delimiter='\t')
        author_writer = csv.writer(author_out, delimiter='\t')
        article_author_writer = csv.writer(article_author_out, delimiter='\t')
        next(reader)
        article_writer.writerow(['id','conference_id','journal_id','key','mdate','pages','title','url','year'])
        author_writer.writerow(['id','name'])
        article_author_writer.writerow(['article_id','author_id'])

        for row in reader:
            article_id = row[0]
            authors = row[1]
            booktitle = row[2]
            journal = row[3]

            if authors and authors != 'null':
                authors_list = authors.split('|')
                for author in authors_list:
                    author = author.strip().strip('"')
                    if  author == '':
                        continue
                    if author not in author_dic:
                        author_dic[author] = author_id
                        author_writer.writerow([author_id, author])
                        author_id += 1
                    article_author_writer.writerow([article_id, author_dic[author]])
                    
            conference_id = '\\N'
            if booktitle and booktitle != 'null':
                if booktitle in conference_dic:
                    conference_id = conference_dic[booktitle]
            
            journal_id = '\\N'
            if journal and journal != 'null':
                best_match = None
                if journal not in journal_titles:
                    normalized_journal = normalize_title(journal)
                    if normalized_journal in normalized_titles:
                        best_match = normalized_titles[normalized_journal]
                    else:
                        escaped_journal = re.escape(journal)
                        pattern_str = escaped_journal.replace(r'\.\ ', r'.* ').replace(r'\.', r'.*')
                        
                        try:
                            regex = re.compile(pattern_str, re.IGNORECASE)
                            matched = False
                            for jt in journal_dic.keys():
                                if regex.search(jt):
                                    best_match = jt
                                    matched = True
                                    break
                            if not matched:
                                close_matches = difflib.get_close_matches(normalized_journal, list(normalized_titles.keys()), n=1, cutoff=0.85)
                                if close_matches:
                                    best_match = normalized_titles[close_matches[0]]
                                    
                        except re.error:
                            pass
                    if best_match is None:
                        journal_titles[journal] = "\\Ν"
                    else:
                        journal_titles[journal] = best_match
                mapped_journal = journal_titles[journal]
                if mapped_journal in journal_dic:
                    journal_id = journal_dic[mapped_journal]
            article_writer.writerow([article_id, conference_id, journal_id, row[4], row[5], row[6], row[7], row[8], row[9]])

def main():
    python_dir = os.path.dirname(os.path.abspath(__file__))
    dir = os.path.dirname(python_dir)

    initial_data_dir = os.path.join(dir, 'initial_data')
    processed_data_dir = os.path.join(dir, 'processed_data')
    csv_to_tsv(os.path.join(initial_data_dir, 'iCore26_KilledColumnsForLoading.csv'),
                os.path.join(processed_data_dir, 'tsv_iCore26_KilledColumnsForLoading.csv'),',')
    csv_to_tsv(os.path.join(initial_data_dir, 'bestSubjectArea.csv'),
                os.path.join(processed_data_dir, 'tsv_bestSubjectArea.csv'),',')
    csv_to_tsv(os.path.join(initial_data_dir, 'journal_ranking_data_raw.csv'),
                os.path.join(processed_data_dir, 'tsv_journal_ranking_data_raw.csv'),',')
    csv_to_tsv(os.path.join(initial_data_dir, 'input_article.csv'),
                os.path.join(processed_data_dir, 'tsv_input_article.csv'),';')
    csv_to_tsv(os.path.join(initial_data_dir, 'input_inproceedings.csv'),
                os.path.join(processed_data_dir, 'tsv_input_inproceedings.csv'),';')
    isolate_bad_records_from_inproceedings(os.path.join(processed_data_dir, 'tsv_input_inproceedings.csv'),
                os.path.join(processed_data_dir, 'cleaned_input_inproceedings.csv'),
                os.path.join(processed_data_dir, 'quarantine_input_inproceedings.csv'))
    isolate_bad_records_from_article(os.path.join(processed_data_dir, 'tsv_input_article.csv'),
                os.path.join(processed_data_dir, 'cleaned_input_article.csv'),
                os.path.join(processed_data_dir, 'quarantine_input_article.csv'))
    clean_journal_ranking_data(os.path.join(processed_data_dir, 'tsv_journal_ranking_data_raw.csv'),
                os.path.join(processed_data_dir, 'cleaned_journal_ranking_data.csv'),
                os.path.join(processed_data_dir, 'quarantine_journal_ranking_data.csv'))
    final_article(os.path.join(processed_data_dir, 'cleaned_input_inproceedings.csv'),
                os.path.join(processed_data_dir, 'cleaned_input_article.csv'),
                os.path.join(processed_data_dir, 'final_article.csv'))
    id_for_best_subject_area(os.path.join(processed_data_dir, 'tsv_bestSubjectArea.csv'),
                            os.path.join(processed_data_dir, 'bestSubjectArea_table.csv'))
    conference_dic = map_id_to_acronym(os.path.join(processed_data_dir, 'tsv_iCore26_KilledColumnsForLoading.csv'))
    bestsubjectarea_dic = map_id_to_best_subject_area(os.path.join(processed_data_dir, 'bestSubjectArea_table.csv'))
    journal_dic = map_id_to_journal_and_bestsubjectarea(os.path.join(processed_data_dir, 'cleaned_journal_ranking_data.csv'),
                                    os.path.join(processed_data_dir, 'journal_ranking_data_table.csv'), bestsubjectarea_dic)
    process_conference_data(os.path.join(initial_data_dir, 'iCore26_KilledColumnsForLoading.csv'),
                        os.path.join(processed_data_dir, 'iCore26_KilledColumnsForLoading_table.csv'),',')
    final_tables(os.path.join(processed_data_dir, 'final_article.csv'),
                 conference_dic, journal_dic,
                 os.path.join(processed_data_dir, 'final_article_table.csv'),
                 os.path.join(processed_data_dir, 'author_table.csv'),
                 os.path.join(processed_data_dir, 'article_author_table.csv'))

if __name__ == "__main__":
    main()



